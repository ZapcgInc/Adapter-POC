package zap.framework.httpclient

import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.io.IOUtils
import org.apache.http.{HttpEntity, HttpResponse}
import org.apache.http.client.methods._
import org.apache.http.entity._
import org.apache.http.impl.client.CloseableHttpClient

trait ZapHttpApacheClient extends ZapHttpClient with LazyLogging {

  val apacheClient: CloseableHttpClient

  override def execute(zrequest: ZapHttpRequest): ZapHttpResponse = {
    val arequest = convertToApacheRequest(zrequest)
    val aresponse = apacheClient.execute(arequest)
    convertToZapHttpResponse(aresponse)
  }

  private def convertToApacheRequest(zrequest: ZapHttpRequest): HttpRequestBase = {
    val request: HttpRequestBase = zrequest.method match {
      case Get => new HttpGet(zrequest.url.build)
      case Post => {
        val post = new HttpPost(zrequest.url.build)
        if (zrequest.body.isDefined) {
          post.setEntity(createEntity(zrequest.body.get))
        }
        post
      }
      case Put => {
        val put = new HttpPut(zrequest.url.build)
        if (zrequest.body.isDefined) {
          put.setEntity(createEntity(zrequest.body.get))
        }
        put
      }
      case _ => throw new RuntimeException(s"Unknow HttpMethod ${zrequest.method}")
    }
    zrequest.headers.map{t => request.setHeader(t._1,t._2)}
    request
  }

  private def createEntity(zhe: ZapHttpEntity): HttpEntity = zhe match {
    case ZapHttpStringEntity(content) => new StringEntity(content, "UTF-8")
    case ZapHttpByteArrayEntity(bytes: Array[Byte]) => new ByteArrayEntity(bytes, ContentType.DEFAULT_TEXT)
  }


  private def convertToZapHttpResponse(aresponse: HttpResponse): ZapHttpResponse = {
    val sc = aresponse.getStatusLine.getStatusCode
    val sm = aresponse.getStatusLine.getReasonPhrase
    val content = if (aresponse.getEntity() != null) {
      Some(ZapHttpStringEntity(IOUtils.toString(aresponse.getEntity.getContent)))
    } else {
      None
    }
    val headers = aresponse.getAllHeaders.map(h => (h.getName,h.getValue))
    new ZapHttpResponse(sc, sm, content, headers:_*)
  }

}

package zap.framework

import org.apache.http.impl.client._
import zap.framework.exceptions.ZapException

package object httpclient {

  var customHttpClient : Option[ZapHttpClient] = None

  var zttpClient = customHttpClient.getOrElse(new ZapHttpApacheClient {
    override val apacheClient: CloseableHttpClient = HttpClients.createDefault()
  })

  implicit class StringToHttpMethod(string: String) {
    def toHttpMethod = {
      string.toLowerCase match {
        case "get" => Get
        case "post" => Post
        case "put" => Put
        case _ => throw new ZapException(s"Invalid method string ${string}")
      }
    }
  }


  def GetReq(url: String) = ZapHttpRequest(Get, url, None)

  def GetReq(url: String, headers: (String, String)*) = ZapHttpRequest(Get, url, None, headers)

  def PostReq(url: String, body: ZapHttpEntity, headers: (String, String)*) = ZapHttpRequest(Get, url, Some(body), headers)

  def PostReq(url: String, headers: (String, String)*) = ZapHttpRequest(Get, url, None, headers)

}

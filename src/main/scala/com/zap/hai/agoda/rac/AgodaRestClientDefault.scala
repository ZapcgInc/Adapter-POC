package com.zap.hai.agoda.rac

import com.zap.hai.agoda.model._
import zap.framework.httpclient._
import zap.framework.props._
import zap.framework.xml._


trait AgodaRestClientDefault extends AgodaRestClient  {

  val httpClient : ZapHttpClient

  override def affliateLongSearch(ar: AvailabilityRequest): Either[ZapHttpResponse,AvailabilityResponse] = {

    val baseurl = zaperties.req("agoda.url")
    val request = ZapHttpRequest(Post,ZapUrl(s"${baseurl}/xmlpartner/xmlservice/search_lrv3"),
      Some(ZapHttpStringEntity(ar.toXml.plain)),("Authorization",zaperties.req[String]("agoda.authorization")))

    val response = httpClient.execute(request)

    if(response.statusCode == 200){
      Right(response.body.get.asString.toAvailabilityResponse)
    } else {
      Left(response)
    }
  }

}

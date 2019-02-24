package com.zap.hai.agoda.rac

import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityResponse}
import zap.framework.httpclient.ZapHttpResponse

trait AgodaRestClient {


  def affliateLongSearch(availabilityRequest : AvailabilityRequest, authorization:String) :Either[ZapHttpResponse,AvailabilityResponse]

}

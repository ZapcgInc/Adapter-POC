package com.zap.hai.controllers

import com.zap.hai.agoda.model.AvailabilityRequest
import zap.framework.httpclient.ZapHttpRequest

trait AvailabilityRequestXfmr extends RequestXfmr[AvailabilityRequest] {


  override def transform(request: ZapHttpRequest): AvailabilityRequest = {



    ???
  }

}

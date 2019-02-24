package com.zap.hai.controllers

import zap.framework.httpclient.{ZapHttpRequest, ZapHttpResponse}

trait ShoppingController {

  def priceCheck(request: ControllerRequest) : ZapHttpResponse

  def getPropertyAvailability(request : ControllerRequest) : ZapHttpResponse

}

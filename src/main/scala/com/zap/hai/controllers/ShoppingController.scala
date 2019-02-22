package com.zap.hai.controllers

import zap.framework.httpclient.{ZapHttpRequest, ZapHttpResponse}

trait ShoppingController {

  def priceCheck(request: ZapHttpRequest) : ZapHttpResponse

  def getPropertyAvailability(request : ZapHttpRequest) : ZapHttpResponse

}

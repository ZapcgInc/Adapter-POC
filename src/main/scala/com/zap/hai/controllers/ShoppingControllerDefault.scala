package com.zap.hai.controllers

import zap.framework.httpclient._

trait ShoppingControllerDefault extends ShoppingController {


  override  def getPropertyAvailability(request: ControllerRequest) : ZapHttpResponse = {
    //validate



    //sZapHttpResponse(200,"",ZapHttpStringEntity(request),("Test","test"))
    ???

  }

  override def priceCheck(request: ControllerRequest): ZapHttpResponse = {
    //extract fields to make message
    //validate request 400

    //create PriceCheckRequest
    //call priceService
    //

    ???
  }

}

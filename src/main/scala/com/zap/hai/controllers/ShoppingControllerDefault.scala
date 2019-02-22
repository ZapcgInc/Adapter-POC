package com.zap.hai.controllers

import zap.framework.httpclient.{ZapHttpRequest, ZapHttpResponse}

trait ShoppingControllerDefault extends ShoppingController {


  override  def getPropertyAvailability(request : ZapHttpRequest) : ZapHttpResponse = {
    //validate


    ???

  }

  override def priceCheck(request: ZapHttpRequest): ZapHttpResponse = {
    //extract fields to make message
    //validate request 400

    //create PriceCheckRequest
    //call priceService
    //

    ???
  }

}

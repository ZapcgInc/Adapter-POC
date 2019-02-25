package com.zap.hai.controllers

import com.zap.hai.agoda.model.AvailabilityRequest
import com.zap.hai.services.ShoppingService
import zap.framework.httpclient._

trait ShoppingControllerDefault extends ShoppingController {

  val shoppingService : ShoppingService

  override  def getPropertyAvailability(request: ControllerRequest) : ZapHttpResponse = {
    //validate
    //convert to availability request
    //invoke service
    //common exception handling.

    var availabilityRequest : AvailabilityRequest = null
    val availibilityResponse = shoppingService.getPropertyAvailability(availabilityRequest)
    //convert to zap http response

    //ZapHttpResponse(200,"",ZapHttpStringEntity(request),("Test","test"))
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

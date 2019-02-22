package com.zap.hai.services

trait ShoppingService {

  //getPropertyRoomRatesAndAvailability
  def getPropertyAvailability(request : AvailabilityRequest) : AvailabilityResponse

  //getCurrentPriceForPreBooking()
  def priceCheck(request : PriceCheckRequest) : PriceCheckResponse

}


case class AvailabilityRequest()

case class AvailabilityResponse()

case class PriceCheckRequest()

case class PriceCheckResponse()
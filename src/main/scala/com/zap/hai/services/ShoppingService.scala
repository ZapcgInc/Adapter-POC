package com.zap.hai.services

import com.zap.hai.agoda.model._
import com.zap.hai.eps.ShoppingResponse

trait ShoppingService {

//  //getPropertyRoomRatesAndAvailability
    def getPropertyAvailability(request : AvailabilityRequest) : ShoppingResponse
//
//    //getCurrentPriceForPreBooking()
//    def priceCheck(request : PriceCheckRequest) : PriceCheckResponse

}



package com.zap.hai.services

import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityResponse}
import com.zap.hai.agoda.rac.AgodaRestClient
import com.zap.hai.eps.ShoppingResponse
import com.zap.hai.transformers.AgodaToEpsXfmr

trait ShoppingServiceDefault extends ShoppingService {

  val agodaRac : AgodaRestClient
  val availToShopResponseXfmr : AgodaToEpsXfmr[AvailabilityResponse,ShoppingResponse]

  override def getPropertyAvailability(request : AvailabilityRequest) : ShoppingResponse = {

    agodaRac.affliateLongSearch(request) match {
      case Right(r) => {
        availToShopResponseXfmr.transform(r)
      }
      case Left(l) => {
        throw new RuntimeException()
      }
    }

  }

}

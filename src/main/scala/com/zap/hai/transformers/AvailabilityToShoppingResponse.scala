package com.zap.hai.transformers

import com.zap.hai.agoda.model.AvailabilityResponse
import com.zap.hai.eps.ShoppingResponse

trait AvailabilityToShoppingResponse extends AgodaToEpsXfmr[AvailabilityResponse,ShoppingResponse] {

  override def transform(i: AvailabilityResponse): ShoppingResponse = ???

}

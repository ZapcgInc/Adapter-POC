package com.zap.hai

import com.zap.hai.agoda.model.AvailabilityResponse
import com.zap.hai.agoda.rac.AgodaRestClient
import com.zap.hai.controllers.ShoppingController
import com.zap.hai.eps.ShoppingResponse
import com.zap.hai.finagle.FinagleRoutes
import com.zap.hai.services.ShoppingService
import com.zap.hai.transformers.AgodaToEpsXfmr
import zap.framework.httpclient.ZapHttpClient

trait HaiFactory {

  val availToShopResponseXfmr : AgodaToEpsXfmr[AvailabilityResponse,ShoppingResponse]
  val finagleService : FinagleRoutes
  val shoppingController : ShoppingController
  val shoppingService: ShoppingService
  val agodaRac: AgodaRestClient
  val httpClient: ZapHttpClient

}

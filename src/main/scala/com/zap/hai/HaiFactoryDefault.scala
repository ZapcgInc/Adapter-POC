package com.zap.hai

import cats.effect.IO
import com.zap.hai.agoda.model.{AvailabilityRequest, AvailabilityResponse}
import com.zap.hai.agoda.rac.{AgodaRestClient, AgodaRestClientDefault}
import com.zap.hai.controllers.{ShoppingController, ShoppingControllerDefault}
import com.zap.hai.eps.ShoppingResponse
import com.zap.hai.finagle.FinagleService
import com.zap.hai.http4s.routes.ShoppingRoutes
import com.zap.hai.services.{ShoppingService, ShoppingServiceDefault}
import com.zap.hai.transformers.{AgodaToEpsXfmr, AvailabilityToShoppingResponse}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.http4s.HttpService
import zap.framework.httpclient.{ZapHttpApacheClient, ZapHttpClient, ZapHttpRequest, ZapHttpResponse}

trait HaiFactoryDefault extends HaiFactory { self =>

  override val httpClient: ZapHttpClient = new ZapHttpApacheClient {
    override val apacheClient: CloseableHttpClient = HttpClients.createDefault()
  }

  override val agodaRac: AgodaRestClient = new AgodaRestClientDefault {
    override val httpClient: ZapHttpClient = self.httpClient
  }

  val availToShopResponseXfmr : AgodaToEpsXfmr[AvailabilityResponse,ShoppingResponse] = new AvailabilityToShoppingResponse {}

  override val shoppingService: ShoppingService = new ShoppingServiceDefault {
    override val agodaRac: AgodaRestClient = self.agodaRac
    override val availToShopResponseXfmr: AgodaToEpsXfmr[AvailabilityResponse, ShoppingResponse] = self.availToShopResponseXfmr
  }

  override val shoppingController : ShoppingController = new ShoppingControllerDefault {
    override val shoppingService: ShoppingService = self.shoppingService
  }

  override val shoppingRoutes: ShoppingRoutes = new ShoppingRoutes {
    override val shoppingController: ShoppingController = self.shoppingController
  }

  override val finagleService: FinagleService = new FinagleService {
    override val routes: HttpService[IO] = shoppingRoutes.service
  }

}

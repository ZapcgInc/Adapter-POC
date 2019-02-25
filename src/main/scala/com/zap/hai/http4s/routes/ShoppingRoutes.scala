package com.zap.hai.http4s.routes

import cats.effect.IO
import com.zap.hai.controllers.ShoppingController
import org.http4s.HttpService
import org.http4s.dsl.io._

trait ShoppingRoutes {

  val shoppingController : ShoppingController

  val service: HttpService[IO] = HttpService[IO] {
    case req@POST -> Root / "properties" / "availability" => {

      Ok("hello")
    }
  }

}

package com.zap.hai.finch.routes

import cats.effect.IO
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.finch.catsEffect._

trait Routes {

  def ping: Endpoint[IO, String] = Endpoint.get("ping") {
    Ok("ping pong")
  }

  val service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](ping)
    .toService



}

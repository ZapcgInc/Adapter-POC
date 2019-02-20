package com.hopper.request

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import io.finch._
import cats.effect.IO
import com.twitter.finagle.Http
import cats.effect.IO
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._

trait Routes {

  def healthcheck: Endpoint[IO, String] = Endpoint.get(
    "book" :: path[String] ::
      param("pfoo") ::
      header("Accept"))  {(id:String, pfoo:String, hfoo:String) =>
    println(s"header = ${hfoo}, param = ${pfoo}, id=${id}")

    Ok("health check")
  }

  def ping: Endpoint[IO, String] = Endpoint.get("ping") {
    Ok("ping pong")
  }


  val service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](healthcheck)
    .serve[Text.Plain](ping)
    .toService


}

package com.hopper

import java.net.InetSocketAddress

import com.hopper.auth.{AuthHandler, AvailabilityRequestHandler, ExceptionHandler}
import com.hopper.handler.{BookRequestHandler, PreBookRequestHandler}
import com.twitter.finagle.Service
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.http._
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service._

object HttpServer
{
    val router: RoutingService[Request with Request] = RoutingService.byPathObject[Request]
      {
          case Root / "properties" / "availability" / hotelID / "rooms" / roomID / "rates" / rateID / "price-check" =>
          {
              new PreBookRequestHandler(hotelID, roomID, rateID)
          }
          case Root / "properties" / "availability" =>
          {
              new AvailabilityRequestHandler
          }
          case Root / "itineraries" =>
          {
              new BookRequestHandler
          }
          case _ =>
          {
              NotFoundService
          }
      }

    val handleExceptions = new ExceptionHandler
    val authorize = new AuthHandler
    val service: Service[Request, Response] = handleExceptions
      .andThen(authorize)
      .andThen(router);

    def start(): Server = ServerBuilder()
      .codec(new RichHttp[Request](Http()))
      .name("AgodaAdapter")
      .bindTo(new InetSocketAddress(7001))
      .build(service)

    def main(args: Array[String])
    {
        val server = start()
    }
}

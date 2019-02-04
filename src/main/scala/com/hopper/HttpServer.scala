package com.hopper

import java.net.InetSocketAddress

import com.hopper.auth.AuthHandler
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http._
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service.NotFoundService
import com.hopper.auth.AvailabilityRequestHandler

object HttpServer
{

    import com.hopper.auth.ExceptionHandler

    val router = RoutingService.byPathObject[Request]
    {
        case Root / "properties" / "availability"/ propertyID => new AvailabilityRequestHandler(propertyID);
        case _ => NotFoundService
    }

    val handleExceptions = new ExceptionHandler
    val authorize = new AuthHandler
    val service: Service[Request, Response] = handleExceptions
      .andThen(authorize)
      .andThen(router);

    def start() = ServerBuilder()
      .codec(new RichHttp[Request](Http()))
      .name("AgodaAdapter")
      .bindTo(new InetSocketAddress(7001))
      .build(service)

    def main(args: Array[String])
    {
        val server = start()
    }
}
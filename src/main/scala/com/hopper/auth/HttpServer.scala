package com.hopper.auth

import java.net.InetSocketAddress

import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http.{Http, Method, Request, Response, RichHttp}

object HttpServer
{
    val routes: Service[Request, Response] = RoutingService.byMethodAndPath
    {
        case (Method.Get, "/properties/availability") =>
        {
            {
                new ShoppingRequestHandler
            }
        }
    }

    val handleExceptions = new ExceptionHandler
    val authorize = new AuthHandler
    val service: Service[Request, Response] = handleExceptions
      .andThen(authorize)
      .andThen(routes);

    def start() = ServerBuilder()
      .codec(new RichHttp[Request](Http()))
      .name("HttpServer")
      .bindTo(new InetSocketAddress(7001))
      .build(handleExceptions.andThen(authorize).andThen(routes))

    def main(args: Array[String])
    {
        val server = start()
    }
}

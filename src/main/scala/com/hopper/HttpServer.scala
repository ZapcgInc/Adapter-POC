package com.hopper

import java.net.InetSocketAddress

import com.hopper.auth.{AuthHandler, AvailabilityRequestHandler, ExceptionHandler}
import com.hopper.handler.PriceCheckRequestHandler
import com.twitter.finagle.Service
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.http._
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service._
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpResponseStatus, HttpVersion}

object HttpServer
{
    def echoService(hotelID: String) = new Service[Request, Response]
    {
        def apply(req: Request): Future[Response] =
        {
            val rep = Response(HttpVersion.HTTP_1_0, Status.Ok)
            rep.setContentString(hotelID)

            Future(rep)
        }
    }

    def testService(hotelID: String, roomID: String, rateID : String) = new Service[Request, Response]
    {
        def apply(req: Request): Future[Response] =
        {
            val rep = Response(HttpVersion.HTTP_1_0, Status.Ok)
            rep.setContentString(hotelID + "," + roomID + "," + rateID)

            Future(rep)
        }
    }

    val router: RoutingService[Request with Request] = RoutingService.byPathObject[Request]
      {
          case Root / "echo" / message / "lol" =>
          {
              echoService(message)
          }
          case Root / "properties" / "availability" / hotelID / "rooms" / roomID / "rates" / rateID / "price-check" =>
          {
              new PriceCheckRequestHandler(hotelID, roomID, rateID)
          }
          case Root / "properties" / "availability" =>
          {
              new AvailabilityRequestHandler
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

package com.zap.hai.finagle

import cats.data.OptionT
import cats.effect.IO
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Status, Request => FRequest, Response => FResponse}
import com.twitter.util.Future
import org.http4s.{HttpService, Header => H4sHeader, Headers => H4sHeaders, Method => H4sMethod, Request => H4sRequest, Response => H4sResponse, Uri => H4sUri}

trait FinagleService {

  val routes : HttpService[IO]

  def service = new Service[FRequest, FResponse] {
    def apply(finagleRequest: FRequest) : Future[FResponse] = {
      Future.value{

        val h4sMeth = finagleRequest.method.name match {
          case "GET" => H4sMethod.GET
          case "POST" => H4sMethod.POST
          case _ => throw new RuntimeException("Not supported")
        }

        val h4sRequest = {
          val headers = finagleRequest.headerMap.map{e => H4sHeader(e._1,e._2)}.toList
          H4sRequest[IO](
            method = h4sMeth,
            uri = H4sUri.unsafeFromString(finagleRequest.uri),
            headers = H4sHeaders(headers)).
            withBody(finagleRequest.contentString)
        }

        routes.run(h4sRequest.unsafeRunSync())
          .getOrElse(H4sResponse.notFound)
          .flatMap{resp =>
            resp.as[String].map{b=>
              val finagleResponse = FResponse(finagleRequest.version, Status(resp.status.code))
              resp.headers.map{h => finagleResponse.headerMap.put(h.name.value,h.value)}
              finagleResponse.contentString = b
              finagleResponse
            }
          }.unsafeRunSync()
      }
    }
  }
}

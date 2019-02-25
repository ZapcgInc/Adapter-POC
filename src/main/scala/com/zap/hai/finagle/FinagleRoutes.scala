package com.zap.hai.finagle

import cats.data.OptionT
import com.twitter.finagle.Service
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http._
import com.twitter.util.Future
import com.zap.hai.controllers.{ControllerRequest, ShoppingController}
import zap.framework.httpclient.{ZapHttpResponse, ZapHttpStringEntity}

import scala.collection.JavaConverters._

trait FinagleRoutes {


  val shoppingController : ShoppingController

  val routes = RoutingService.byMethodAndPathObject[Request] {
    case (Method.Get, Root / "property" / "availability") => {
      delegate(){(crequest:ControllerRequest) => shoppingController.getPropertyAvailability(crequest)}
    }
    case (Method.Get, Root / "properties" / "availability" / hotelId / "rooms" / roomId / "rates" / rateId / "price-check") => {
      delegate(Map("hotelId"->hotelId,"roomId"->roomId,"rateId"->rateId)){(crequest:ControllerRequest)=>
        shoppingController.getPropertyAvailability(crequest)
      }
    }
    case _ => new Service[Request, Response] {
      def apply(request: Request): Future[Response] = Future(Response(Status.NotFound))
    }
  }


  def delegate(pathParams : Map[String,String] = Map())(f:ControllerRequest=>ZapHttpResponse)  = {
    new Service[Request, Response] {
      def apply(req: Request): Future[Response] = {
        Future {
          val parameters = req.getParams().asScala.map(t=>(t.getKey,t.getValue)).toMap ++ req.headerMap.toMap ++ pathParams
          val zresponse = f(ControllerRequest(parameters, Some(req.contentString)))

          val fresponse = Response(Status(zresponse.statusCode))
          zresponse.body.map{b => fresponse.contentString = b.asInstanceOf[ZapHttpStringEntity].content}
          zresponse.headers.map{t => fresponse.headerMap.set(t._1,t._2)}
          fresponse
        }
      }
    }
  }


}


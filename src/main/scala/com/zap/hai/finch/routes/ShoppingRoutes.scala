package com.zap.hai.finch.routes

import cats.effect.IO
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.finch.catsEffect._
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
import com.zap.hai.controllers.ShoppingController
import io.finch._
import io.finch.catsEffect._
import shapeless.HNil
import zap.framework.httpclient._

import scala.collection.mutable.ArrayBuffer


trait ShoppingRoutes {

  val shoppingController : ShoppingController

  def propertiesAvailability: Endpoint[IO, String] = Endpoint.get(
    "properties" :: "availability" ::
      param("checkin") :: param("checkout") :: param("currency") :: param("language") :: param("country_code") ::
      param("occupancy") :: paramOption("property_id") :: param("sales_channel") :: param("sales_environment") ::
      param("sort_type") :: paramOption("filter") :: paramOption("include") :: paramOption("rate_option") ::
      paramOption("billing_terms") :: paramOption("payment_terms") :: paramOption("partner_point_of_sale") ::

      header("Accept") :: header("Accept-encoding") :: header("Authorization") :: header("Customer-Ip") ::
      headerOption("Customer-Session-Id") :: header("User-Agent")
  ) { (checkin: String, checkout: String, currency: String, language: String, countryCode: String, occupancy: String, propertyId: Option[String], salesChannel: String,
       salesEnvironment: String, sortType: String, filter: Option[String], include: Option[String], rateOption: Option[String], billingTerms: Option[String],
       paymentTerms: Option[String], partnerPos: Option[String], accept: String, acceptEncoding: String, authorization: String, customerIp: String,
       customerSessionId: Option[String], userAgent: String) =>


    val queryParams = ArrayBuffer[(String, String)](("checkin", checkin), ("checkout", checkout), ("currency", currency), ("language", language),
      ("country_code", countryCode), ("occupancy", occupancy), ("sales_channel", salesChannel), ("sales_environment", salesEnvironment),
      ("sort_type", sortType))

    propertyId.map(e => queryParams += (("property_id", e)))
    filter.map(e => queryParams += (("filter", e)))
    include.map(e => queryParams += (("include", e)))
    rateOption.map(e => queryParams += (("rate_option", e)))
    billingTerms.map(e => queryParams += (("billing_terms", e)))
    paymentTerms.map(e => queryParams += (("payment_terms", e)))
    partnerPos.map(e => queryParams += (("partner_point_of_sale", e)))

    val headers = ArrayBuffer[(String, String)]()
    //TODO add headers

    val request = new ZapHttpRequest(Get, ZapUrl("", queryParams.toList: _*), None, headers.toList: _*)
    val response = shoppingController.getPropertyAvailability(request)

    val output = if(response.statusCode == 200){
      Output.payload[String](response.body.get.toString, Status.Ok)
    } else {
      Output.payload[String]("", Status.InternalServerError)
    }
    response.headers.map(e=> output.withHeader(e._1,e._2))

    output
  }


  //GET /properties/availability/{propertyId}/rooms/{roomId}/rates/{rateId}/price-check
  def priceCheck: Endpoint[IO, String] = Endpoint.get(
    "properties" :: "availability" :: path("propertyId") :: "rooms" :: path("roomId") :: "rates" :: path("rateId") :: "price-check" ::

      param("token") ::

      header("Accept") :: header("Accept-encoding") :: header("Authorization") ::
      header("Customer-Ip") :: headerOption("Customer-Session-Id") :: header("User-Agent") ::
      headerOption("Test")
  ) { (propertyId: String, roomId: String, rateId: String,
       token: String,
       accept: String, acceptEncoding: String, authorization: String, customerIp: String, customerSessionId: Option[String],
       userAgent: String, test: Option[String]) =>

    Ok("")
  }

  val service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](priceCheck)
    .toService


}

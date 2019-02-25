package com.zap.hai.finch.routes

//import cats.effect.IO
//import com.twitter.finagle.Service
//import com.twitter.finagle.http.{Request, Response}
//import io.finch._
//import io.finch.catsEffect._
//import com.twitter.finagle.{Http, Service}
//import com.twitter.finagle.http.{Request, Response, Status}
//import com.twitter.server.TwitterServer
//import com.twitter.util.{Await, Future}
//import io.finch._
//import cats.effect.IO
//import com.twitter.finagle.Http
//import cats.effect.IO
//import com.twitter.finagle.{Http, Service}
//import com.twitter.finagle.http.{Request, Response}
//import com.twitter.util.Await
//import io.finch._
//import io.finch.catsEffect._
//import shapeless.HNil

trait Routes {


//  def propertiesAvailability: Endpoint[IO, String] = Endpoint.get(
//    "properties" :: "availability" ::
//      param("checkin") :: param("checkout") :: param("currency") :: param("language") :: param("country_code") ::
//      param("occupancy") :: paramOption("property_id") :: param("sales_channel") :: param("sales_environment") ::
//      param("sort_type") :: paramOption("filter") :: paramOption("include") :: paramOption("rate_option") ::
//      paramOption("billing_terms") :: paramOption("payment_terms") :: paramOption("partner_point_of_sale") ::
//      header("Accept") :: header("Accept-encoding") :: header("Authorization") :: header("Customer-Ip") ::
//      headerOption("Customer-Session-Id") :: header("User-Agent")
//  ) { (checkin: String, checkout: String, currency: String, language: String, countryCode: String, occupancy: String, propertyId: Option[String], salesChannel: String,
//       salesEnvironment: String, sortType: String, filter: Option[String], include: Option[String], rateOption: Option[String], billingTerms: Option[String],
//       paymentTerms: Option[String], partnerPos: Option[String], accept: String, acceptEncoding: String, authorization: String, customerIp: String,
//       customerSessionId: Option[String], userAgent: String) =>
//
//    Ok("properties availability")
//  }
//
//  def ping: Endpoint[IO, String] = Endpoint.get("ping") {
//    Ok("ping pong")
//  }
//
//  val service: Service[Request, Response] = Bootstrap
//    .serve[Text.Plain](ping)
//    .toService


}

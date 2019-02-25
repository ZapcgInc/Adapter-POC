package com.hopper.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.hopper.commons.eps.model.booking.{EPSBookingResponse, Links}
import com.hopper.converter.href.BookHrefBuilder
import com.hopper.model.agoda.booking.{BookingDetails, BookingRequest, BookingResponse, CustomerDetail, GuestDetail, Phone}
import com.hopper.util.AgodaPOSTRequestUtil
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpResponseStatus, HttpVersion}


class BookRequestHandler extends Service[Request, Response]
{
    private val RESPONSE_JSON_MAPPER = (new ObjectMapper).registerModule(DefaultScalaModule)
    private val END_POINT = "https://sandbox-affiliateapisecure.agoda.com/xmlpartner/xmlbookservice/book_v3"

    override def apply(request: Request): Future[Response] =
    {
        val token: BookingDetails = BookHrefBuilder.getBookRequestDetail(request.getParam("token"))
        val bookRequest: BookingRequest = new BookingRequest(token, _populateCustomerDetails(), _populateGuestDetails())

        val agodaRequestXML: String = bookRequest.convertToXML()

        println(agodaRequestXML)
        val agodaResponseAsString: String = AgodaPOSTRequestUtil.postXMLRequestAndGetResponse(agodaRequestXML, END_POINT)

        println(agodaResponseAsString)

        val agodaResponse: BookingResponse = BookingResponse.unmarshall(agodaResponseAsString)

        Future.value(_convertToEPSResponse(agodaResponse))
    }

    def _convertToEPSResponse(agodaResponse: BookingResponse): Response =
    {
        if (agodaResponse.status != 200)
        {
            //TODO.
            val response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.BAD_REQUEST))
            response.setContentTypeJson()
            response.setContentString("Booking Error")

            return response
        }

        val links: Map[String, Links] = Map("retrieve" -> new Links(Method.Get.toString, agodaResponse.details.info.selfservice))

        // Create and Convert EPS Response to JSON
        val jsonResponse: String = RESPONSE_JSON_MAPPER.writeValueAsString(new EPSBookingResponse(agodaResponse.details.info.itineraryID, links))

        val response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK))
        response.setContentTypeJson()
        response.setContentString(jsonResponse)

        response
    }

    def _populateCustomerDetails(): CustomerDetail =
    {
        val customerDetail: CustomerDetail = new CustomerDetail
        customerDetail.title = "Mr."
        customerDetail.firstName = "John"
        customerDetail.lastName = "Mayer"
        customerDetail.email = "johnmayer@test.com"
        customerDetail.language = "en-us"

        val phone: Phone = new Phone
        phone.areaCode = "080"
        phone.countryCode = "001"
        phone.number = "123456"

        customerDetail.phone = phone

        customerDetail
    }

    def _populateGuestDetails(): Array[GuestDetail] =
    {
        val guestDetail: GuestDetail = new GuestDetail
        guestDetail.title = "Mr."
        guestDetail.firstName = "John"
        guestDetail.lastName = "Mayer"
        guestDetail.countryOfPassport = "US"
        guestDetail.gender = "Male"
        guestDetail.age = 45
        guestDetail.primary = true

        Array(guestDetail)
    }
}

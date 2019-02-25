package com.hopper.handler

import com.hopper.converter.href.BookHrefBuilder
import com.hopper.model.agoda.booking.{BookingDetails, BookingRequest, CustomerDetail}
import com.hopper.util.AgodaPOSTRequestUtil
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpVersion}

class BookRequestHandler extends Service[Request, Response]
{
    private val END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlbookservice/book_v3"

    override def apply(request: Request): Future[Response] =
    {
        import org.jboss.netty.handler.codec.http.HttpResponseStatus
        val token: BookingDetails = BookHrefBuilder.getBookRequestDetail(request.getParam("token"))
        val bookRequest: BookingRequest = new BookingRequest(token, _populateCustomerDetails())

        val agodaRequestXML: String = bookRequest.convertToXML()

        val agodaResponseAsString: String = AgodaPOSTRequestUtil.postXMLRequestAndGetResponse(agodaRequestXML, END_POINT)

        val response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK))
        response.setContentTypeJson()
        response.setContentString("Response from Agoda : " + agodaResponseAsString)

        Future.value(response)
    }

    def _populateCustomerDetails() : CustomerDetail =
    {
        val customerDetail: CustomerDetail = new CustomerDetail
        customerDetail.title = "Mr"
        customerDetail.firstName = "John"
        customerDetail.lastName = "Mayer"
        customerDetail.language = "en-US"

        customerDetail
    }
}

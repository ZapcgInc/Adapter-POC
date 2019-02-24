package com.hopper.handler

import java.io.StringReader

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.hopper.converter.{AvailabilityResponseConverter, PriceCheckHrefConverter}
import com.hopper.converter.PriceCheckHrefConverter.PriceCheckToken
import com.hopper.model.availability.agoda.request.AvailabilityRequestV2
import com.hopper.model.availability.agoda.response.AvailabilityLongResponseV2
import com.hopper.model.availability.eps.EPSShoppingResponse
import com.hopper.util.AgodaPOSTRequestUtil
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import javax.xml.bind.{JAXBContext, Unmarshaller}
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpResponseStatus, HttpVersion}

class PriceCheckRequestHandler(hotelID: String, roomID: String, rateId: String) extends Service[Request, Response]
{
    private val END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3"
    private val AGODA_RESPONSE_UNMARSHALLER: Unmarshaller = JAXBContext.newInstance(classOf[AvailabilityLongResponseV2]).createUnmarshaller()

    override def apply(request: Request): Future[Response] =
    {
        val token: PriceCheckToken = PriceCheckHrefConverter.getShopRequestDetail(request.getParam("token"))

        val agodaRequest: AvailabilityRequestV2 = new AvailabilityRequestV2(hotelID, token)
        val agodaRequestXML: String = agodaRequest.convertToXML()

        // Post to Agoda API and get response
        val agodaResponseAsString: String = AgodaPOSTRequestUtil.postXMLRequestAndGetResponse(agodaRequestXML, END_POINT)
        val agodaResponse: AvailabilityLongResponseV2 = AGODA_RESPONSE_UNMARSHALLER
          .unmarshal(new StringReader(agodaResponseAsString))
          .asInstanceOf[AvailabilityLongResponseV2]

        val epsResponse: EPSShoppingResponse = new AvailabilityResponseConverter(agodaRequest, agodaResponse).convertToEPSResponse(roomID)

        // TODO : compare shopping responses and return.
        // Currently returning without any checks.
        Future.value(_convertToEPSResponse(HttpResponseStatus.OK, epsResponse.properties))
    }

    def _convertToEPSResponse(httpResponseStatus: HttpResponseStatus, agodaResponse: AnyRef): Response =
    {
        // Convert EPS Response to JSON
        val jsonResponse = (new ObjectMapper).registerModule(DefaultScalaModule).writeValueAsString(agodaResponse)

        val response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, httpResponseStatus))
        response.setContentTypeJson()
        response.setContentString(jsonResponse)

        response
    }
}

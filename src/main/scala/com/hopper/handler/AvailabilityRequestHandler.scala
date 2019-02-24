package com.hopper.auth

import java.io.{StringReader, StringWriter}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.hopper.converter.AvailabilityResponseConverter
import com.hopper.model.availability.agoda.response.AvailabilityLongResponseV2
import com.hopper.model.availability.eps.EPSShoppingResponse
import com.hopper.model.error.EPSErrorResponse
import com.hopper.util.AgodaPOSTRequestUtil
import com.hopper.validators.{AvailabilityRequestDataValidator, RequestTestHeaderValidator}
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import javax.xml.bind.{JAXBContext, JAXBException, Marshaller, Unmarshaller}
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpResponseStatus, HttpVersion}

class AvailabilityRequestHandler extends Service[Request, Response]
{

    import com.hopper.model.availability.agoda.request.AvailabilityRequestV2

    private val END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3"

    private val AGODA_REQUEST_MARSHALLER: Marshaller = JAXBContext.newInstance(classOf[AvailabilityRequestV2]).createMarshaller
    private val AGODA_RESPONSE_UNMARSHALLER: Unmarshaller = JAXBContext.newInstance(classOf[AvailabilityLongResponseV2]).createUnmarshaller()

    override def apply(request: Request): Future[Response] =
    {

        var validationResponse: Option[(HttpResponseStatus, EPSErrorResponse)] = RequestTestHeaderValidator.validate(request)
        if (validationResponse.isDefined)
        {
            return Future.value(_convertToEPSResponse(validationResponse.get._1, validationResponse.get._2))
        }

        validationResponse = AvailabilityRequestDataValidator.validate(request)
        if (validationResponse.isDefined)
        {
            return Future.value(_convertToEPSResponse(validationResponse.get._1, validationResponse.get._2))
        }

        // Create Agoda Request from EAN HTTP Request.
        val agodaAvailabilityRequest: AvailabilityRequestV2 = new AvailabilityRequestV2(request)

        // Convert agoda POJO to XML.
        val agodaRequestXML: String = agodaAvailabilityRequest.convertToXML()

        // Post to Agoda API and get response
        val agodaResponseAsString:String = AgodaPOSTRequestUtil.postXMLRequestAndGetResponse(agodaRequestXML, END_POINT)
        val agodaResponse: AvailabilityLongResponseV2 = AGODA_RESPONSE_UNMARSHALLER
          .unmarshal(new StringReader(agodaResponseAsString))
          .asInstanceOf[AvailabilityLongResponseV2]

        // Convert to EPS Response
        val epsResponse: EPSShoppingResponse = new AvailabilityResponseConverter(agodaAvailabilityRequest, agodaResponse).convertToEPSResponse()

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

    def _convertToAgodaXMLRequest(agodaRequest: AvailabilityRequestV2): String =
    {
        try
        {
            val stringWriter: StringWriter = new StringWriter
            AGODA_REQUEST_MARSHALLER.marshal(agodaRequest, stringWriter)
            stringWriter.toString
        }
        catch
        {
            case jexp: JAXBException =>
            {
                throw new RuntimeException("Failed to marshall availability request")
            }
        }
    }
}
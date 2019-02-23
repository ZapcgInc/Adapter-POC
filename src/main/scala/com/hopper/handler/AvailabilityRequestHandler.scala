package com.hopper.auth

import java.io.{IOException, StringReader, StringWriter}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.google.common.net.{HttpHeaders, MediaType}
import com.hopper.model.availability.agoda.request.AvailabilityRequestV2
import com.hopper.model.availability.agoda.response.AvailabilityLongResponseV2
import com.hopper.model.availability.eps.EPSShoppingResponse
import com.hopper.model.error.EPSErrorResponse
import com.hopper.validators.{AvailabilityRequestDataValidator, RequestTestHeaderValidator}
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.util.Future
import javax.xml.bind.{JAXBContext, JAXBException, Marshaller, Unmarshaller}
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpResponseStatus, HttpVersion}

class AvailabilityRequestHandler extends Service[Request, Response]
{
    private val END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3"

    private val AGODA_REQUEST_MARSHALLER: Marshaller = JAXBContext.newInstance(classOf[AvailabilityRequestV2]).createMarshaller
    private val AGODA_RESPONSE_UNMARSHALLER: Unmarshaller = JAXBContext.newInstance(classOf[AvailabilityLongResponseV2]).createUnmarshaller()

    override def apply(request: Request): Future[Response] =
    {
        import com.hopper.converter.AvailabilityResponseConverter
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
        val agodaRequestXML: String = _convertToAgodaXMLRequest(agodaAvailabilityRequest)

        // Post to Agoda API and get response
        val agodaResponse: AvailabilityLongResponseV2 = _postXMLRequest(agodaRequestXML)

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
            val stringWriter = new StringWriter
            AGODA_REQUEST_MARSHALLER.marshal(agodaRequest, stringWriter)

            // Debug
            println("Agoda Request:" + stringWriter.toString)

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

    def _postXMLRequest(agodaXMLRequest: String): AvailabilityLongResponseV2 =
    {
        val url = new java.net.URL(END_POINT)
        val connection = url.openConnection.asInstanceOf[java.net.HttpURLConnection]
        try
        {
            connection.setRequestMethod(Method.Post.getName)
            connection.setDoOutput(true)
            connection.setRequestProperty(HttpHeaders.AUTHORIZATION, "1812488:6fae573e-b261-4c02-97b4-3dd20d1e74b2")
            connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_UTF_8.toString)
            connection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, MediaType.GZIP.toString)
            connection.getOutputStream.write(agodaXMLRequest.getBytes)
            connection.getOutputStream.close

            _getResponse(connection)
        }
        catch
        {
            case exp: IOException =>
            {
                exp.printStackTrace()
                throw new RuntimeException("Error connecting to Agoda Adapter.")
            }
        }
    }

    def _getResponse(connection: java.net.HttpURLConnection): AvailabilityLongResponseV2 =
    {
        import com.hopper.model.availability.agoda.response.AvailabilityLongResponseV2
        var inputStream = if (connection.getResponseCode == 200)
                          {
                              connection.getInputStream
                          }
                          else
                          {
                              connection.getErrorStream
                          }
        var responseStreamAsString = scala.io.Source.fromInputStream(inputStream).getLines().mkString("\n")

        println("Response From Agoda: " + responseStreamAsString)

        val response: AvailabilityLongResponseV2 = AGODA_RESPONSE_UNMARSHALLER.unmarshal(new StringReader(responseStreamAsString)).asInstanceOf[AvailabilityLongResponseV2]


        response
    }
}
package com.hopper.auth

import java.io.{IOException, StringReader, StringWriter}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.hopper.handler.AvailabilityRequestHandlerHelper
import com.hopper.model.availability.agoda.request.AvailabilityRequestV2
import com.hopper.model.availability.agoda.response.AvailabilityLongResponseV2
import com.hopper.model.constants.GlobalConstants
import com.hopper.model.error.{EPSErrorResponse, ResponseErrorFields, ResponseErrors}
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
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
        import com.hopper.model.availability.eps.EPSShoppingResponse

        dataValidation(request) match {
            case Some(s) => {
                val jsonResponse = (new ObjectMapper).registerModule(DefaultScalaModule).writeValueAsString(s)
                val response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK))
                response.setContentTypeJson()
                response.setContentString(jsonResponse)
                Future.value(response)
            }
                case None => {val agodaAvailabilityRequest: AvailabilityRequestV2 = new AvailabilityRequestV2(request)

                    // Convert agoda POJO to XML.
                    val agodaRequestXML: String = _convertToAgodaXMLRequest(agodaAvailabilityRequest)

                    // Post to Agoda API and get response
                    val agodaResponse: AvailabilityLongResponseV2 = _postXMLRequest(agodaRequestXML)

                    // Convert to EPS Response
                    val epsResponse: EPSShoppingResponse = AvailabilityRequestHandlerHelper.convertToEPSResponse(agodaAvailabilityRequest, agodaResponse)

                    // Convert EPS Response to JSON
                    val jsonResponse = (new ObjectMapper).registerModule(DefaultScalaModule).writeValueAsString(epsResponse.properties)

                    // Create and Return Valid HTTP Response
                    val response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK))
                    response.setContentTypeJson()
                    response.setContentString(jsonResponse)

                    Future.value(response)}
        }


        // Create Agoda Request from EAN HTTP Request.

    }

    def dataValidation(request: Request): Option[EPSErrorResponse]=
    {
        var errorResponse : Option[EPSErrorResponse] = None
        if(request.getParams("property_id")==None || request.getParams("property_id").isEmpty)
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "property_id"
            rf.value= "null"
            re.errorType= "property_id.required"
            re.fields = Array(rf)
            re.message = "Property Id is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("checkin")==null || request.getParam("checkin").isEmpty )
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "checkin"
            rf.value= "null"
            re.errorType= "checkin.required"
            re.fields = Array(rf)
            re.message = "Checkin is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("checkout")==null || request.getParam("checkout").isEmpty )
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "checkout"
            rf.value= "null"
            re.errorType= "checkout.required"
            re.fields = Array(rf)
            re.message = "Checkout is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("occupancy")==null || request.getParam("occupancy").isEmpty  )
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "occupancy"
            rf.value= "null"
            re.errorType= "occupancy.required"
            re.fields = Array(rf)
            re.message = "Occupancy is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("language")==null || request.getParam("language").isEmpty)
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "language"
            rf.value= "null"
            re.errorType= "language.required"
            re.fields = Array(rf)
            re.message = "Language is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("country_code")==null||request.getParam("country_code").isEmpty)
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "country_code"
            rf.value= "null"
            re.errorType= "country_code.required"
            re.fields = Array(rf)
            re.message = "Country Code is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("sales_channel")==null||request.getParam("sales_channel").isEmpty)
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "sales_channel"
            rf.value= "null"
            re.errorType= "sales_channel.required"
            re.fields = Array(rf)
            re.message = "Sales Channel is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("currency")==null||request.getParam("currency").isEmpty)
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "currency"
            rf.value= "null"
            re.errorType= "currency.required"
            re.fields = Array(rf)
            re.message = "Currency Code is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("sales_environment")==null||request.getParam("sales_environment").isEmpty)
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "sales_environment"
            rf.value= "null"
            re.errorType= "sales_environment.required"
            re.fields = Array(rf)
            re.message = "Sales Environment is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        if(request.getParam("sort_type")==null||request.getParam("sort_type").isEmpty)
        {
            var er = new EPSErrorResponse
            er.errorType="invalid_input"
            er.message="An invalid request was sent in, please check the nested errors for details."
            var re = new ResponseErrors
            var rf = new ResponseErrorFields
            rf.errorType = "querystring"
            rf.name = "sort_type"
            rf.value= "null"
            re.errorType= "sort_type.required"
            re.fields = Array(rf)
            re.message = "Sort Type is required."
            er.errors= Array(re)
            errorResponse = Some(er)
        }
        errorResponse
    }

    def _convertToAgodaXMLRequest(agodaRequest: AvailabilityRequestV2): String =
    {
        try
        {
            val stringWriter = new StringWriter
            AGODA_REQUEST_MARSHALLER.marshal(agodaRequest, stringWriter)

            // Debug
            println("Agoda Request:"+stringWriter.toString)

            return stringWriter.toString
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
            connection.setRequestMethod("POST")
            connection.setDoOutput(true)
            connection.setRequestMethod(GlobalConstants.HTTP_POST)
            connection.setRequestProperty(GlobalConstants.AUTH_KEY, "1812488:6fae573e-b261-4c02-97b4-3dd20d1e74b2")
            connection.setRequestProperty(GlobalConstants.CONTENT_TYPE, "text/xml; charset=utf-8")
            connection.getOutputStream.write(agodaXMLRequest.getBytes)
            connection.getOutputStream.close

            return _getResponse(connection)
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


        return response
    }
}
package com.hopper.auth

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import com.hopper.AvailabilityProcessor
import javax.xml.bind.JAXBContext
import java.io.StringWriter
import javax.xml.bind.Marshaller
import com.hopper.model.availability.agoda.AvailabilityRequestV2
import com.hopper.constants.GlobalConstants

class AvailabilityRequestHandler extends Service[Request, Response]
{
    private val END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3"
    private val AGODA_REQUEST_MARSHALLER: Marshaller = JAXBContext.newInstance(classOf[AvailabilityRequestV2]).createMarshaller

    override def apply(request: Request): Future[Response] =
    {
        // Create Agoda Request from EAN HTTP Request.
        val agodaAvailabilityRequest: AvailabilityRequestV2 = new AvailabilityRequestV2(request)

        // Convert agoda POJO to XML.
        val agodaRequestXML: String = _convertToAgodaXMLRequest(agodaAvailabilityRequest)

        println(agodaRequestXML)

        _postXMLRequest(agodaRequestXML)
        Future.value(AvailabilityProcessor.process(request))
    }

    def _convertToAgodaXMLRequest(agodaRequest: AvailabilityRequestV2): String =
    {
        val stringWriter = new StringWriter
        AGODA_REQUEST_MARSHALLER.marshal(agodaRequest, stringWriter)

        return stringWriter.toString
    }

    def _postXMLRequest(agodaXMLRequest: String): Unit =
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

            val inputStream = if (connection.getResponseCode == 200) connection.getInputStream else connection.getErrorStream
            println(scala.io.Source.fromInputStream(inputStream).getLines().mkString("\n"))
        }
        // TODO : catch Exceptions
    }
}
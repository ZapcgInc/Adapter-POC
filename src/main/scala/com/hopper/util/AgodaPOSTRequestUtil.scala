package com.hopper.util

import java.io.IOException

import com.google.common.net.{HttpHeaders, MediaType}
import com.twitter.finagle.http.Method

object AgodaPOSTRequestUtil
{
    def postXMLRequestAndGetResponse(agodaXMLRequest: String, endPoint: String): String =
    {
        val url = new java.net.URL(endPoint)
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


    def _getResponse(connection: java.net.HttpURLConnection): String =
    {

        val inputStream = if (connection.getResponseCode == 200)
                          {
                              connection.getInputStream
                          }
                          else
                          {
                              connection.getErrorStream
                          }


        scala.io.Source.fromInputStream(inputStream).getLines().mkString("\n")
    }
}

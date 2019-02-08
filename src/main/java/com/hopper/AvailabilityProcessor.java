package com.hopper;

import com.hopper.constants.GlobalConstants;
import com.hopper.model.availability.AvailabilityRequest;
import com.hopper.model.availability.AvailabilityResponse;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import scala.util.parsing.json.JSONObject;

import javax.xml.bind.JAXB;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static scala.collection.immutable.Map.Map1;

public class AvailabilityProcessor
{
    private static final String END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3";

    public static Response process(final Request request) throws Exception
    {
        // TODO : return Rapid HTTP Response code.
        final Response response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK));
        response.setContentTypeJson();
        response.setContentString(_getResponseJSON(request));

        return response;
    }

    private static String _getResponseJSON(final Request request) throws Exception
    {
        return _postRequest(request).toString();
    }

    private static JSONObject _postRequest(final Request request) throws Exception
    {
        final AvailabilityRequest availabilityRequest = AvailabilityRequest.create(request);
        final URL url = new URL(END_POINT);
        System.out.println("Availability Request: "+availabilityRequest);
        JAXB.marshal(availabilityRequest, System.out); // Debug
        JAXB.marshal(availabilityRequest, url);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GlobalConstants.HTTP_POST);
        connection.setRequestProperty(GlobalConstants.CONTENT_TYPE, GlobalConstants.CONTENT_FORMAT);
        connection.setRequestProperty(GlobalConstants.AUTH_KEY, "1812488:6fae573e-b261-4c02-97b4-3dd20d1e74b2");
        connection.setRequestProperty(GlobalConstants.ACCEPT_ENCODING,GlobalConstants.ACCEPT_FORMAT);
        return _parseAndGenerateRapidResponse(connection);
    }

    private static JSONObject _parseAndGenerateRapidResponse(final HttpURLConnection connection) throws Exception
    {
        final int responseCode = connection.getResponseCode();
        System.out.println("API Response code" + responseCode);
        final InputStream inputStream = responseCode == 200 ? connection.getInputStream() : connection.getErrorStream();
        final AvailabilityResponse response = JAXB.unmarshal(inputStream, AvailabilityResponse.class);

        System.out.println("Status : " + response.getStatus()); // DEBUG
      //  String responseBody = connection.getContent().toString();
      //  System.out.println("Response Body :" + responseBody);

        // TODO: parse and convert to RAPID Response.
        // Temporary stub.
        final JSONObject jsonObject = new JSONObject(new Map1<String, Object>("Response Status", responseCode));
        return jsonObject;
    }

}

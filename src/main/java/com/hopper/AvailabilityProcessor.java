package com.hopper;

import com.hopper.constants.GlobalConstants;
import com.hopper.model.availability.AvailabilityRequest;
import com.hopper.model.availability.AvailabilityResponse;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import scala.collection.immutable.Map;
import scala.util.parsing.json.JSONObject;
import sun.net.www.http.HttpCaptureInputStream;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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

        StringWriter  sw=new StringWriter();
        String  result;
        try {
            JAXBContext carContext = JAXBContext.newInstance(AvailabilityRequest.class);
            Marshaller carMarshaller = carContext.createMarshaller();
            carMarshaller.marshal(availabilityRequest, sw);
         result = sw.toString();
            System.out.println(result);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        JAXB.marshal(availabilityRequest, System.out); // Debug
        JAXB.marshal(availabilityRequest, url);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GlobalConstants.HTTP_POST);
        System.out.println("Properties:"+connection.getRequestProperties());
        connection.setRequestProperty(GlobalConstants.AUTH_KEY, "1812488:6fae573e-b261-4c02-97b4-3dd20d1e74b2");
        connection.setRequestProperty(GlobalConstants.CONTENT_TYPE, "text/xml; charset=utf-8");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(result);
        wr.flush();
        return _parseAndGenerateRapidResponse(connection);
    }

    private static JSONObject _parseAndGenerateRapidResponse(final HttpURLConnection connection) throws Exception
    {

        final int responseCode = connection.getResponseCode();
        System.out.println("API Response code" + responseCode);
        InputStream inputStream = responseCode == 200 ? connection.getInputStream() : connection.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder data = new StringBuilder();
        String s = "";
        while((s = br.readLine()) != null)
            data.append(s);
        String content = data.toString();
      //  System.out.println("content"+content);
        Map1<String,Object> m=new Map1<>("content",content);

        // TODO: parse and convert to RAPID Response.
        // Temporary stub.
        final JSONObject jsonObject = new JSONObject(m);
        return jsonObject;
    }

}

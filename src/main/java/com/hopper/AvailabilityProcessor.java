package com.hopper;

import com.hopper.constants.GlobalConstants;
import com.hopper.model.availability.AvailabilityRequest;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import scala.util.parsing.json.JSONObject;

import javax.xml.bind.JAXB;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static scala.collection.immutable.Map.Map1;

public class AvailabilityProcessor
{
    private static final String END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3";

    public static Response process(final Request request, final String propertyID) throws Exception
    {
        final Response response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK));
        response.setContentTypeJson();
        response.setContentString(_getResponseJSON(propertyID, request));
        return response;
    }

    private static String _getResponseJSON(final String propertyID, final Request request) throws Exception
    {
        final AvailabilityRequest availabilityRequest = _createRequest(propertyID, request);
        _postRequest(availabilityRequest);

        final JSONObject jsonObject = new JSONObject(new Map1<String, Object>("ID", propertyID));
        return jsonObject.toString();
    }

    private static void _postRequest(final AvailabilityRequest availabilityRequest) throws Exception
    {
        final URL url = new URL(END_POINT);
        JAXB.marshal(availabilityRequest, System.out);
        JAXB.marshal(availabilityRequest, url);

        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

        final int responseCode = connection.getResponseCode();
        final InputStream inputStream = responseCode == 200 ? connection.getInputStream() : connection.getErrorStream();
        final InputStreamReader reader = new InputStreamReader(inputStream);
        String result = new BufferedReader(reader)
                .lines()
                .parallel()
                .collect(Collectors.joining("\n"));

        System.out.println(result);

    }

    private static AvailabilityRequest _createRequest(final String propertyID, final Request request)
    {
        final AvailabilityRequest availabilityRequest = new AvailabilityRequest();

        for (Map.Entry header : request.getHeaders())
        {
            final String key = (String) header.getKey();
            final String value = (String) header.getValue();

            switch (key)
            {
                case GlobalConstants.CHECKIN_PARAM_KEY:
                {
                    availabilityRequest.setCheckInDate(value);
                    break;
                }

                case GlobalConstants.CHECKOUT_PARAM_KEY:
                {
                    availabilityRequest.setCheckOutDate(value);
                    break;
                }
                case GlobalConstants.CURRENCY_CODE_KEY:
                {
                    availabilityRequest.setCurrency(value);
                    break;
                }
                case GlobalConstants.LANGUAGE_CODE_KEY:
                {
                    availabilityRequest.setLanguage(value);
                    break;
                }
                case GlobalConstants.OCCUPANCY_KEY:
                {
                    _populateOccupancy(value, availabilityRequest);
                    break;
                }
                case GlobalConstants.AUTH_KEY:
                {
                    availabilityRequest.setApiKey(value);
                    break;
                }
            }
        }

        request.getParams()
                .stream()
                .filter(e -> e.getKey().equals("property_id"))
                .map(e -> (String) e.getValue())
                .forEach(availabilityRequest::setPropertyIds);

        return availabilityRequest;
    }

    private static void _populateOccupancy(final String reqOccupancy, final AvailabilityRequest availabilityRequest)
    {
        if (StringUtils.isEmpty(reqOccupancy))
        {
            return;
        }
        final String[] split = reqOccupancy.split("-");

        availabilityRequest.setAdultsCount(Integer.parseInt(split[0]));
        if (split.length == 2)
        {
            availabilityRequest.setChildrenAges(
                    Arrays.stream(split[1].split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList())
            );
        }
    }
}

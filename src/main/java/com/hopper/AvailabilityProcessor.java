package com.hopper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopper.constants.GlobalConstants;
import com.hopper.model.availability.*;
import com.hopper.model.availability.agoda.Availability;
import com.hopper.model.availability.agoda.Hotel;
import com.hopper.model.availability.shopping.*;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import javax.xml.bind.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.DAYS;
import static scala.collection.immutable.Map.Map1;

public class AvailabilityProcessor
{
    private static final String END_POINT = "http://sandbox-affiliateapi.agoda.com/xmlpartner/xmlservice/search_lrv3";


    public static Response process(final Request request) throws Exception
    {
        // TODO : return Rapid HTTP Response code.
//        final Response response = Response.apply(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK));
//        response.setContentTypeJson();
//        response.setContentString(_getResponseJSON(request));

        return null;
    }

    private static String _getResponseJSON(final Request request) throws Exception
    {
        return _postRequest(request);
    }

    private static String _postRequest(final Request request) throws Exception
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
        connection.setRequestProperty(GlobalConstants.AUTH_KEY, "1812488:6fae573e-b261-4c02-97b4-3dd20d1e74b2");
        connection.setRequestProperty(GlobalConstants.CONTENT_TYPE, "text/xml; charset=utf-8");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(result);
        wr.flush();
        return _parseAndGenerateRapidResponse(connection,availabilityRequest);
    }

    private static String _parseAndGenerateRapidResponse(final HttpURLConnection connection,AvailabilityRequest availabilityRequest) throws Exception
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


        JAXBContext jaxbContext     = JAXBContext.newInstance( Availability.class );
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Availability response = (Availability) jaxbUnmarshaller.unmarshal(new StringReader(content));
        System.out.println("Response-------"+"\n"+response.toString());

        List<Property> propertyList = new ArrayList<>();
        for (Hotel hotel : response.getHotels()) {

            Property property = new Property();
            property.setPropertyId(Integer.toString(hotel.getId()));
            List<Room> roomList = new ArrayList<>();
            hotel.getRoom().getRooms().forEach(room -> {
                Room room1 = new Room();
                List<Rate> ratesList = new ArrayList<>();
                room1.setId(Integer.toString(room.getId()));
                room1.setName(room.getName());
                Rate rate = new Rate();
                rate.setId(room.getRatecategoryid());
                rate.setAvailableRooms(Integer.parseInt(room.getRemainingRooms()) == 0 ? 2147483647 :
                        Integer.parseInt(room.getRemainingRooms()));
                rate.setRefundable(Integer.parseInt(room.getCancellation().getPolicyParameters().
                        getPolicyParameters().get(0).getDays()) == 365 ? false : true);
                rate.setDepositRequired(false);
                rate.setFencedDeal(false);
                rate.setMerchantOfRecord("TBD");
                List<Amenities> amenitiesList = new ArrayList<>();
                if (room.getBenefits() != null) {
                    room.getBenefits().forEach(benefit -> {
                        Amenities amenities = new Amenities();
                        amenities.setId(benefit.getId());
                        amenitiesList.add(amenities);
                    });
                    rate.setAmenities(amenitiesList);
                }

                Map<String, Link> linkMap = new HashMap<>();
                Link link = new Link();
                String id = property.getPropertyId();
                link.setHref("/2.1/properties/availability/"+id+"/rooms/201300484/rates/206295235/payment-options?token=Ql1WAERHXV1QO");
                link.setMethod("GET");
                linkMap.put("payment_options",link);
                rate.setLinks(linkMap);

                List<BedGroups> bedGroupList = new ArrayList<>();
                BedGroups bedGroups = new BedGroups();
                Map<String, Link> linkMap2 = new HashMap<>();
                Link link2 = new Link();
                link2.setMethod("GET");
                link2.setHref("/2.1/properties/availability/"+id+"/rooms/201300484/rates/206295235/price-check?token=Ql1WAERHXV1QO");
                linkMap2.put("price_check",link2);
                bedGroups.setLinks(linkMap2);
                bedGroupList.add(bedGroups);
                rate.setBedGroups(bedGroupList);

                if (Double.parseDouble(room.getRateInfo().getTotalPaymentAmount().getInclusive()) != 0) {
                    List<CancelPolicies> policiesList = new ArrayList<>();
                    if (room.getCancellation().getPolicyDates() != null) {
                        room.getCancellation().getPolicyDates().getPolicyDatesList().forEach(policyDate -> {
                            CancelPolicies cancelPolicies = new CancelPolicies();
                            cancelPolicies.setCurrency(room.getCurrency());
                            cancelPolicies.setStart(policyDate.getAfter() != null ? policyDate.getAfter() : "");
                            cancelPolicies.setEnd(policyDate.getBefore()!=null ? policyDate.getBefore() : "");
                            cancelPolicies.setAmount(Double.parseDouble(policyDate.getRate().getInclusive()));
                            //TODO:
                            //cancelPenalties.setNights();
                            //cancelPenalties.setPercent();
                            policiesList.add(cancelPolicies);
                        });
                        rate.setCancelPolicies(policiesList);
                    }
                }
                RoomPrice roomPrice = new RoomPrice();

                Price basePrice = new Price();
                basePrice.setType("base_rate");
                basePrice.setValue(Double.parseDouble(room.getRateInfo().getTotalPaymentAmount().getExclusive()));
                basePrice.setCurrency(room.getCurrency());

                Price saleTaxPrice = new Price();
                saleTaxPrice.setType("sale_tax");
                saleTaxPrice.setCurrency(room.getCurrency());
                saleTaxPrice.setValue(Double.parseDouble(room.getRateInfo().getTotalPaymentAmount().getTax()));

                Price taxAndServiceFee = new Price();
                taxAndServiceFee.setType("tax_and_service_fee");
                taxAndServiceFee.setCurrency(room.getCurrency());
                taxAndServiceFee.setValue(Double.parseDouble(room.getRateInfo().getTotalPaymentAmount().getFees()));

                List <Price> priceList = new ArrayList<>();
                priceList.add(basePrice);
                priceList.add(saleTaxPrice);
                priceList.add(taxAndServiceFee);

                LocalDate checkin = LocalDate.parse(availabilityRequest.getCheckInDate());
                LocalDate checkout = LocalDate.parse(availabilityRequest.getCheckOutDate());
                long lengthOfStay = 0;
                lengthOfStay = Duration.between(checkin.atStartOfDay(),checkout.atStartOfDay()).toDays();
                System.out.println("LOS"+lengthOfStay);

                List<List<Price>> nightPriceList  = new ArrayList<>();
                long n = lengthOfStay;
                while(n!=0){
                    nightPriceList.add(priceList);
                    n--;
                }
                roomPrice.setNightlyPrice(nightPriceList);
                Map<String,RoomPrice> occupancyMap = new HashMap<>();
                List<String> occupancyList = availabilityRequest.getOccupancy();
                System.out.println("OCC"+occupancyList);
                occupancyList.forEach(occupancy->{
                    occupancyMap.put(occupancy,roomPrice);
                });
                rate.setRoomPriceByOccupancy(occupancyMap);
                if(room.getRateInfo().getPromotionType()!=null) {
                    rate.setPromoId(room.getRateInfo().getPromotionType().getId());
                    rate.setPromoDesc(room.getRateInfo().getPromotionType().getText());
                }
                ratesList.add(rate);
                room1.setRates(ratesList);

                roomList.add(room1);
            });
            property.setRooms(roomList);
            propertyList.add(property);

        }
        ShoppingResponse shoppingResponse = new ShoppingResponse(propertyList.toArray(new Property[propertyList.size()]));


        System.out.println(shoppingResponse);
        shoppingResponse.getProperties();

        ObjectMapper objectMapper = new ObjectMapper();

        String responseFinal = objectMapper.writeValueAsString(shoppingResponse.getProperties());

        return responseFinal;
    }
}

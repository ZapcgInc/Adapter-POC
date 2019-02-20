package com.hopper.model.availability;

import com.google.common.base.Preconditions;
import com.hopper.constants.GlobalConstants;
import com.hopper.model.availability.shopping.Room;
import com.twitter.finagle.http.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement(name = "AvailabilityRequestV2")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailabilityRequest {
    @XmlAttribute(name = "siteid")
    private String siteId = "1812488";

    @XmlAttribute(name = "apikey")
    private String apiKey = "6fae573e-b261-4c02-97b4-3dd20d1e74b2";

    @XmlAttribute(name = "xmlns")
    private String xmlns = "http://xml.agoda.com";

    @XmlAttribute(name = "xmlns:xsi")
    private String xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";

    @XmlElement(name = "Type")
    private Integer type;

    @XmlElement(name = "Id")
    private String propertyIds;

    @XmlElement(name = "CheckIn")
    private String checkInDate;

    @XmlElement(name = "CheckOut")
    private String checkOutDate;

    @XmlElement(name = "Rooms")
    private Integer roomCount;

    @XmlElement(name = "Adults")
    private Integer adultsCount;

    @XmlElement(name = "Children")
    private Integer childrenCount;

    @XmlTransient
    private List<Integer> childrenAges;

    @XmlElement(name = "Language")
    private String language;

    @XmlElement(name = "Currency")
    private String currency;

    @XmlTransient
    private List<String> occupancy;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    public int getType() {
        return type;
    }

    public void setType(final RequestType type) {
        this.type = type.getID();
    }

    public String getPropertyIds() {
        return propertyIds;
    }

    public void setPropertyIds(List<String> propertyIds) {
        this.propertyIds = String.join(",", propertyIds);
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language.toLowerCase();
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public List<Integer> getChildrenAges() {
        return childrenAges;
    }

    public void setChildrenAges(List<Integer> childrenAges) {
        this.childrenAges = childrenAges;
    }

    public List<String> getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(List<String> occupancy) {
        this.occupancy = occupancy;
    }

    public static AvailabilityRequest create(final Request request) {
        final AvailabilityRequest availabilityRequest = new AvailabilityRequest();
        List<String> occupancies = new ArrayList<>();
        for (Map.Entry param : request.getParams()) {

            final String key = (String) param.getKey();
            final String value = (String) param.getValue();
            switch (key) {
                case GlobalConstants.CHECKIN_PARAM_KEY: {
                    availabilityRequest.setCheckInDate(value);
                    break;
                }

                case GlobalConstants.CHECKOUT_PARAM_KEY: {
                    availabilityRequest.setCheckOutDate(value);
                    break;
                }
                case GlobalConstants.CURRENCY_CODE_KEY: {
                    availabilityRequest.setCurrency(value);
                    break;
                }
                case GlobalConstants.LANGUAGE_CODE_KEY: {
                    availabilityRequest.setLanguage(value);
                    break;
                }
                case GlobalConstants.OCCUPANCY_KEY: {
                    System.out.println("Value" + value);
                    occupancies.add(value);
                    _populateOccupancy(value, availabilityRequest);
                    break;
                }
            }

        }
        if (CollectionUtils.isNotEmpty(occupancies)) {
            availabilityRequest.setRoomCount(occupancies.size());
            availabilityRequest.setOccupancy(occupancies);
        }

//        for (Map.Entry header : request.getHeaders())
//        {
//            final String key = (String) header.getKey();
//            final String value = (String) header.getValue();
//
//            switch (key)
//            {
//            case GlobalConstants.AUTH_KEY:
//            {
//                availabilityRequest.setApiKey(value);
//                break;
//            }
//            }
//        }

        final List<String> propertyIds = request.getParams()
                .stream()
                .filter(e -> e.getKey().equals("property_id"))
                .map(e -> (String) e.getValue())
                .collect(Collectors.toList());

        Preconditions.checkArgument(CollectionUtils.isNotEmpty(propertyIds), "At least one property ID must be specified.");
        availabilityRequest.setPropertyIds(propertyIds);
        System.out.println("PropSize" + propertyIds.size());

        availabilityRequest.setType(propertyIds.size() == 1 ? RequestType.HotelSearch : RequestType.HotelListSearch);

        return availabilityRequest;
    }

    private static void _populateOccupancy(final String reqOccupancy, final AvailabilityRequest availabilityRequest) {
        if (StringUtils.isEmpty(reqOccupancy)) {
            return;
        }
        final String[] split = reqOccupancy.split("-");
        System.out.println("Children list: " + Arrays.toString(split));
        availabilityRequest.setAdultsCount(Integer.parseInt(split[0]));
        if (split.length == 2) {
            availabilityRequest.setChildrenAges(
                    Arrays.stream(split[1].split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList())
            );
            //        System.out.println("Child age for room"+room+"-"+availabilityRequest.getChildrenAges());
        }
        availabilityRequest.setChildrenCount(availabilityRequest.getChildrenAges() == null ?
                0 : availabilityRequest.getChildrenAges().size());

    }



    @Override
    public String toString() {
        return "AvailabilityRequest{" +
                "siteId='" + siteId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", xmlns='" + xmlns + '\'' +
                ", xmlnsXsi='" + xmlnsXsi + '\'' +
                ", type=" + type +
                ", propertyIds='" + propertyIds + '\'' +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", roomCount=" + roomCount +
                ", currency='" + currency + '\'' +
                ", language='" + language + '\'' +
                ", adultsCount=" + adultsCount +
                ", childrenCount=" + childrenCount +
                ", childrenAges=" + childrenAges +
                '}';
    }
}

package com.hopper.model.availability;

import com.google.common.base.Preconditions;
import com.hopper.constants.GlobalConstants;
import com.twitter.finagle.http.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement(name = "AvailabilityRequestV2")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailabilityRequest
{
    // TODO : what is siteId ?
    @XmlAttribute(name = "siteid")
    private String siteId = "123456";

    @XmlAttribute(name = "apiKey")
    private String apiKey;

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

    @XmlElement(name = "Currency")
    private String currency;

    @XmlElement(name = "Language")
    private String language;

    @XmlElement(name = "Adults")
    private Integer adultsCount;

    @XmlElement(name = "Children")
    private Integer childrenCount;

    @XmlElementWrapper(name="ChildrenAges")
    @XmlElement(name="age")
    private List<Integer> childrenAges;

    public String getSiteId()
    {
        return siteId;
    }

    public void setSiteId(String siteId)
    {
        this.siteId = siteId;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getXmlns()
    {
        return xmlns;
    }

    public void setXmlns(String xmlns)
    {
        this.xmlns = xmlns;
    }

    public String getXmlnsXsi()
    {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi)
    {
        this.xmlnsXsi = xmlnsXsi;
    }

    public int getType()
    {
        return type;
    }

    public void setType(final RequestType type)
    {
        this.type = type.getID();
    }

    public String getPropertyIds()
    {
        return propertyIds;
    }

    public void setPropertyIds(List<String> propertyIds)
    {
        this.propertyIds = String.join(",", propertyIds);
    }

    public String getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate()
    {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate)
    {
        this.checkOutDate = checkOutDate;
    }

    public int getRoomCount()
    {
        return roomCount;
    }

    public void setRoomCount(int roomCount)
    {
        this.roomCount = roomCount;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public int getAdultsCount()
    {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount)
    {
        this.adultsCount = adultsCount;
    }

    public int getChildrenCount()
    {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount)
    {
        this.childrenCount = childrenCount;
    }

    public List<Integer> getChildrenAges()
    {
        return childrenAges;
    }

    public void setChildrenAges(List<Integer> childrenAges)
    {
        this.childrenAges = childrenAges;
    }

    public static AvailabilityRequest create(final Request request)
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

        final List<String> propertyIds = request.getParams()
                .stream()
                .filter(e -> e.getKey().equals("property_id"))
                .map(e -> (String) e.getValue())
                .collect(Collectors.toList());

        Preconditions.checkArgument(CollectionUtils.isNotEmpty(propertyIds), "At least one property ID must be specified.");
        availabilityRequest.setPropertyIds(propertyIds);

        availabilityRequest.setType(propertyIds.size() == 1 ? RequestType.HotelSearch : RequestType.HotelListSearch);
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

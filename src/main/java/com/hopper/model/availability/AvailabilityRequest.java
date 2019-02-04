package com.hopper.model.availability;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "AvailabilityRequestV2")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailabilityRequest
{
    @XmlAttribute(name = "siteid")
    private String siteId;

    @XmlAttribute(name = "apiKey")
    private String apiKey;

    @XmlAttribute(name = "xmlns")
    private String xmlns;

    @XmlAttribute(name = "xmlns:xsi")
    private String xmlnsXsi;

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

    public void setType(int type)
    {
        this.type = type;
    }

    public String getPropertyIds()
    {
        return propertyIds;
    }

    public void setPropertyIds(String propertyIds)
    {
        this.propertyIds = propertyIds;
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
}

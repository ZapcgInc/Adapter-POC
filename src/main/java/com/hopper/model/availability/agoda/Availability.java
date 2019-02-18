package com.hopper.model.availability.agoda;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "AvailabilityLongResponseV2")
@XmlAccessorType(XmlAccessType.FIELD)
public class Availability {

    @XmlAttribute(name = "status")
    private int status;

    @XmlAttribute(name = "searchid")
    private String searchid;

    @XmlElementWrapper(name = "Hotels")
    @XmlElement(name = "Hotel")
    private List<Hotel> hotels;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSearchid() {
        return searchid;
    }

    public void setSearchid(String searchid) {
        this.searchid = searchid;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "status=" + status +
                ", searchid='" + searchid + '\'' +
                ", hotels=" + hotels +
                '}';
    }
}

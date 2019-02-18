package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Hotels")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hotels {
    @XmlElement(name="Hotel")
    private List<Hotel> hotels;

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        hotels = hotels;
    }

    @Override
    public String toString() {
        return "Hotels{" +
                ", hotels=" + hotels +
                '}';
    }
}

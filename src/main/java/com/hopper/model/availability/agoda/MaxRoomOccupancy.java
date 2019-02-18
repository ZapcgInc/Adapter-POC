package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MaxRoomOccupancy")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaxRoomOccupancy {

    @XmlAttribute(name="normalbedding")
    private String normalBedding;

    @XmlAttribute(name="extrabeds")
    private String extraBeds;


    public String getNormalBedding() {
        return normalBedding;
    }

    public void setNormalBedding(String normalBedding) {
        this.normalBedding = normalBedding;
    }

    public String getExtraBeds() {
        return extraBeds;
    }

    public void setExtraBeds(String extraBeds) {
        this.extraBeds = extraBeds;
    }

    @Override
    public String toString() {
        return "MaxRoomOccupancy{" +
                "normalbedding='" + normalBedding + '\'' +
                ", extrabeds='" + extraBeds + '\'' +
                '}';
    }
}

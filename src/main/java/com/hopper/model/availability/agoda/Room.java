package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Room")
@XmlAccessorType(XmlAccessType.FIELD)
public class Room {
    @XmlAttribute
    private int id;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private int lineitemid;

    @XmlAttribute
    private String rateplan;

    @XmlAttribute
    private String ratetype;

    @XmlAttribute
    private String currency;

    @XmlAttribute
    private String model;

    @XmlAttribute
    private String ratecategoryid;

    @XmlAttribute
    private String blockid;

    @XmlElement(name="StandardTranslation")
    private String  standardTranslation;

    @XmlElement(name="MaxRoomOccupancy")
    private MaxRoomOccupancy maxRoomOccupancy;

    @XmlElement(name="RemainingRooms")
    private String remainingRooms;

    @XmlElement(name="RateInfo")
    private RateInfo rateInfo;

    @XmlElement(name="Cancellation")
    private Cancellation cancellation;

    @XmlElement(name="Benefits")
    private List<Benefit> benefits;

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLineitemid() {
        return lineitemid;
    }

    public void setLineitemid(int lineitemid) {
        this.lineitemid = lineitemid;
    }

    public String getRateplan() {
        return rateplan;
    }

    public void setRateplan(String rateplan) {
        this.rateplan = rateplan;
    }

    public String getRatetype() {
        return ratetype;
    }

    public void setRatetype(String ratetype) {
        this.ratetype = ratetype;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRatecategoryid() {
        return ratecategoryid;
    }

    public void setRatecategoryid(String ratecategoryid) {
        this.ratecategoryid = ratecategoryid;
    }

    public String getBlockid() {
        return blockid;
    }

    public void setBlockid(String blockid) {
        this.blockid = blockid;
    }

    public String getStandardTranslation() {
        return standardTranslation;
    }

    public void setStandardTranslation(String standardTranslation) {
        this.standardTranslation = standardTranslation;
    }

    public MaxRoomOccupancy getMaxRoomOccupancy() {
        return maxRoomOccupancy;
    }

    public void setMaxRoomOccupancy(MaxRoomOccupancy maxRoomOccupancy) {
        this.maxRoomOccupancy = maxRoomOccupancy;
    }

    public String getRemainingRooms() {
        return remainingRooms;
    }

    public void setRemainingRooms(String remainingRooms) {
        this.remainingRooms = remainingRooms;
    }

    public RateInfo getRateInfo() {
        return rateInfo;
    }

    public void setRateInfo(RateInfo rateInfo) {
        this.rateInfo = rateInfo;
    }

    public Cancellation getCancellation() {
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lineitemid=" + lineitemid +
                ", rateplan='" + rateplan + '\'' +
                ", ratetype='" + ratetype + '\'' +
                ", currency='" + currency + '\'' +
                ", model='" + model + '\'' +
                ", ratecategoryid='" + ratecategoryid + '\'' +
                ", blockid='" + blockid + '\'' +
                ", standardTranslation='" + standardTranslation + '\'' +
                ", maxRoomOccupancy=" + maxRoomOccupancy +
                ", remainingRooms='" + remainingRooms + '\'' +
                ", rateInfo=" + rateInfo +
                ", cancellation=" + cancellation +
                '}';
    }
}

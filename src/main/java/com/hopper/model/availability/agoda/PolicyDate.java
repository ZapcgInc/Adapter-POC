package com.hopper.model.availability.agoda;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="PolicyDate")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyDate {


    @Nullable
    @XmlAttribute(name="before", required = false)
    private String before;

    @Nullable
    @XmlAttribute(name="after",required = false)
    private String after;

    @XmlElement(name = "Rate")
    private Rate rate;

    public String getAfter() {
        return after;
    }

    public void setAfter( String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore( String before) {
        this.before = before;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "PolicyDate{" +
                "before='" + before + '\'' +
                ", after='" + after + '\'' +
                ", rate=" + rate +
                '}';
    }
}

package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="PolicyParameter")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyParameter {

    @XmlAttribute
    private String charge;

    @XmlAttribute
    private String days;

    @XmlValue
    private String content;

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PolicyParameter{" +
                "charge='" + charge + '\'' +
                ", days='" + days + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

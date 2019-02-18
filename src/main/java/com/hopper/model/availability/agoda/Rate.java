package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Rate")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rate {
    @XmlAttribute
    private String inclusive;

    @XmlAttribute
    private String fees;

    @XmlAttribute
    private String exclusive;

    @XmlAttribute
    private String tax;

    public String getInclusive() {
        return inclusive;
    }

    public void setInclusive(String inclusive) {
        this.inclusive = inclusive;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getExclusive() {
        return exclusive;
    }

    public void setExclusive(String exclusive) {
        this.exclusive = exclusive;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "inclusive='" + inclusive + '\'' +
                ", fees='" + fees + '\'' +
                ", exclusive='" + exclusive + '\'' +
                ", tax='" + tax + '\'' +
                '}';
    }
}

package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CheapestRoom")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheapestRoom {

    @XmlAttribute(name="exclusive")
    double exclusive;
    @XmlAttribute(name="tax")
    double tax;
    @XmlAttribute(name="fees")
    double fees;
    @XmlAttribute(name="inclusive")
    double inclusive;

    public double getExclusive() {
        return exclusive;
    }

    public void setExclusive(double exclusive) {
        this.exclusive = exclusive;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public double getInclusive() {
        return inclusive;
    }

    public void setInclusive(double inclusive) {
        this.inclusive = inclusive;
    }

    @Override
    public String toString() {
        return "CheapestRoom{" +
                "exclusive=" + exclusive +
                ", tax=" + tax +
                ", fees=" + fees +
                ", inclusive=" + inclusive +
                '}';
    }
}

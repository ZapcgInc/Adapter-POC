package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Cancellation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cancellation {
    @XmlElement(name ="PolicyParameters")
    private PolicyParameters policyParameters;

    @XmlElement(name ="PolicyText")
    private PolicyText policyText;

    @XmlElement(name ="PolicyTranslated")
    private PolicyTranslated policyTranslated;

    @XmlElement(name ="PolicyDates")
    private PolicyDates policyDates;

    public PolicyParameters getPolicyParameters() {
        return policyParameters;
    }

    public void setPolicyParameters(PolicyParameters policyParameters) {
        this.policyParameters = policyParameters;
    }

    public PolicyText getPolicyText() {
        return policyText;
    }

    public void setPolicyText(PolicyText policyText) {
        this.policyText = policyText;
    }

    public PolicyTranslated getPolicyTranslated() {
        return policyTranslated;
    }

    public void setPolicyTranslated(PolicyTranslated policyTranslated) {
        this.policyTranslated = policyTranslated;
    }

    public PolicyDates getPolicyDates() {
        return policyDates;
    }

    public void setPolicyDates(PolicyDates policyDates) {
        this.policyDates = policyDates;
    }

    @Override
    public String toString() {
        return "Cancellation{" +
                "policyParameters=" + policyParameters +
                ", policyText=" + policyText +
                ", policyTranslated=" + policyTranslated +
                ", policyDates=" + policyDates +
                '}';
    }
}

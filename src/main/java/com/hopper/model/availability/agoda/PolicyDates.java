package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="PolicyDates")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyDates {
    @XmlElement(name ="PolicyDate")
    private List<PolicyDate> policyDatesList;

    public List<PolicyDate> getPolicyDatesList() {
        return policyDatesList;
    }

    public void setPolicyDatesList(List<PolicyDate> policyDatesList) {
        this.policyDatesList = policyDatesList;
    }

    @Override
    public String toString() {
        return "PolicyDates{" +
                "policyDates=" + policyDatesList +
                '}';
    }
}

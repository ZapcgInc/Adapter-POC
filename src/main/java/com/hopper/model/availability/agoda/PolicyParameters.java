package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="PolicyParameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyParameters {
    @XmlElement(name="PolicyParameter")
    private List<PolicyParameter> policyParameters;

    public List<PolicyParameter> getPolicyParameters() {
        return policyParameters;
    }

    public void setPolicyParameters(List<PolicyParameter> policyParameters) {
        this.policyParameters = policyParameters;
    }

    @Override
    public String toString() {
        return "PolicyParameters{" +
                "policyParameters=" + policyParameters +
                '}';
    }
}

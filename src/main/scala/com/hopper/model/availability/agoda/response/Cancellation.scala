package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlElementWrapper, XmlRootElement}

@XmlRootElement(name = "Cancellation")
@XmlAccessorType(XmlAccessType.NONE)
class Cancellation
{
    @XmlElementWrapper(name = "Hotels")
    @XmlElement(name = "Hotel")
    var policyParameters: java.util.List[PolicyParameter] = _

    @XmlElement(name = "PolicyText")
    var policyText: PolicyText = _

    @XmlElement(name = "PolicyTranslated")
    var policyTranslated: PolicyTranslated = _

    @XmlElementWrapper(name = "PolicyDates")
    @XmlElement(name = "PolicyDate")
    var policyDates: java.util.List[PolicyDate] = _


    override def toString = s"Cancellation(policyParameters=$policyParameters, policyText=$policyText, policyTranslated=$policyTranslated, policyDates=$policyDates)"
}

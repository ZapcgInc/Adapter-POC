package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}
import javax.xml.bind.annotation.XmlElementWrapper

@XmlRootElement(name = "AvailabilityLongResponseV2")
@XmlAccessorType(XmlAccessType.FIELD)
class AvailabilityLongResponseV2
{
    @XmlAttribute(name = "status")
    var status: Int = 0

    @XmlAttribute(name = "searchid")
    var searchID: String = _

    @XmlElementWrapper(name = "Hotels")
    @XmlElement(name = "Hotel")
    var hotels: Array[Hotel] = _

    @XmlElementWrapper(name = "ErrorMessages")
    @XmlElement(name = "ErrorMessage")
    var errors: Array[ErrorMessage] = _

    override def toString = s"AvailabilityLongResponseV2($status, $searchID, $hotels)"
}

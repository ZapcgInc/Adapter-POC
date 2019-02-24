package com.hopper.model.agoda.booking
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement, XmlElement}

@XmlRootElement(name = "BookingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
class BookingDetails
{
    @XmlAttribute(name = "searchid")
    var searchID: String = _

    @XmlElement(name = "AllowDuplication")
    var allowDuplication:Boolean = true

    @XmlElement(name = "CheckIn")
    var checkInDate: String = _

    @XmlElement(name = "CheckOut")
    var checkOutDate: String = _

    @XmlElement(name = "UserCountry")
    var userCountry:String = _
}

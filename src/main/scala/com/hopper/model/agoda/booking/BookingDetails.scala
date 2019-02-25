package com.hopper.model.agoda.booking
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement, XmlElement}

@XmlRootElement(name = "BookingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
class BookingDetails
{
    @XmlAttribute(name = "tag")
    var tag: String = "0000-0000-00"

    @XmlAttribute(name = "searchid")
    var searchID: String = _

    @XmlAttribute(name = "AllowDuplication")
    var allowDuplication:Boolean = true

    @XmlAttribute(name = "CheckIn")
    var checkInDate: String = _

    @XmlAttribute(name = "CheckOut")
    var checkOutDate: String = _

    @XmlAttribute(name = "UserCountry")
    var userCountry:String = _

    @XmlElement(name = "Hotel")
    var hotel:Hotel = _
}

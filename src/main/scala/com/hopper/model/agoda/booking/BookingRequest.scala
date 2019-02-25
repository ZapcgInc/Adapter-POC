package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}

@XmlRootElement(name = "BookingRequestV3")
@XmlAccessorType(XmlAccessType.FIELD)
class BookingRequest
{
    @XmlAttribute(name = "siteid")
    var siteId: String = "1812488"

    @XmlAttribute(name = "apikey")
    var apiKey: String = "6fae573e-b261-4c02-97b4-3dd20d1e74b2"

    @XmlAttribute(name = "delaybooking")
    var delayBooking: Boolean = true

    @XmlElement(name = "BookingDetails")
    var bookingDetails: BookingDetails = _

    @XmlElement(name = "CustomerDetail")
    var customerDetail: CustomerDetail = _
}

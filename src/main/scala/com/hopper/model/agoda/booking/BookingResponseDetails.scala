package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement(name = "BookingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
class BookingResponseDetails
{
    @XmlElement(name = "Booking")
    var info: BookingResponseInfo = _


    override def toString = s"BookingResponseDetails(info=$info)"
}

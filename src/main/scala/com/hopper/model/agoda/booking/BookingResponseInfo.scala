package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

@XmlRootElement(name = "Booking")
@XmlAccessorType(XmlAccessType.FIELD)
class BookingResponseInfo
{
    @XmlAttribute(name = "id")
    var id: String = _

    @XmlAttribute(name = "ItineraryID")
    var itineraryID: String = _

    @XmlAttribute(name = "selfservice")
    var selfservice: String = _


    override def toString = s"BookingResponseInfo(id=$id, itineraryID=$itineraryID, selfservice=$selfservice)"
}

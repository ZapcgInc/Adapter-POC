package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlElementWrapper, XmlRootElement}

@XmlRootElement(name = "BookingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
class Hotel
{
    @XmlAttribute(name = "id")
    var id: String = _

    @XmlElementWrapper(name = "Rooms")
    @XmlElement(name = "Room")
    var rooms: Array[Room] = _
}

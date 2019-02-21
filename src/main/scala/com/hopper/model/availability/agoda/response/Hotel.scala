package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlElementWrapper, XmlRootElement}

@XmlRootElement(name = "Hotel")
@XmlAccessorType(XmlAccessType.FIELD)
class Hotel
{
    @XmlElement(name = "Id")
    var id: String = _

    @XmlElement(name = "CheapestRoom")
    var cheapestRoom: CheapestRoom = _

    @XmlElement(name = "PaxSettings")
    var paxSettings: PaxSettings = _

    @XmlElementWrapper(name = "Rooms")
    @XmlElement(name = "Room")
    var rooms: java.util.List[Room] = _

    override def toString = s"Hotel(id=$id, cheapestRoom=$cheapestRoom, paxSettings=$paxSettings, rooms=$rooms)"
}
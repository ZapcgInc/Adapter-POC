package com.hopper.model.agoda.booking
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

@XmlRootElement(name = "BookingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
class Hotel
{
    @XmlAttribute(name = "id")
    var id:String = _
}

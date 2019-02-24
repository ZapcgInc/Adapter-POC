package com.hopper.model.agoda.availability.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

@XmlRootElement(name = "MaxRoomOccupancy")
@XmlAccessorType(XmlAccessType.NONE)
class MaxRoomOccupancy
{
    @XmlAttribute(name = "normalbedding")
    var normalBedding: String = _

    @XmlAttribute(name = "extrabeds")
    var extraBeds: String = _
}
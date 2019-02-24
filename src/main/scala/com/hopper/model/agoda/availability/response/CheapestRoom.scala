package com.hopper.model.agoda.availability.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

@XmlRootElement(name = "CheapestRoom")
@XmlAccessorType(XmlAccessType.NONE)
class CheapestRoom
{
    @XmlAttribute(name = "exclusive")
    var exclusive: Double = _

    @XmlAttribute(name = "tax")
    var tax: Double = _

    @XmlAttribute(name = "fees")
    var fees: Double = _

    @XmlAttribute(name = "inclusive")
    var inclusive: Double = _
}
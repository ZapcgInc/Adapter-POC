package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

@XmlRootElement(name = "Rate")
@XmlAccessorType(XmlAccessType.NONE)
class Rate
{
    @XmlAttribute(name = "inclusive")
    var inclusive: Double = _

    @XmlAttribute(name = "fees")
    var fees: Double = _

    @XmlAttribute(name = "exclusive")
    var exclusive: Double = _

    @XmlAttribute(name = "tax")
    var tax: Double = _
}

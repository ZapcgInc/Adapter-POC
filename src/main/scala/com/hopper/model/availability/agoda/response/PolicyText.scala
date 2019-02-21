package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement, XmlValue}

@XmlRootElement(name = "PolicyText")
@XmlAccessorType(XmlAccessType.NONE)
class PolicyText
{
    @XmlAttribute(name = "language")
    var language: String = _

    @XmlValue
    var content: String = _
}

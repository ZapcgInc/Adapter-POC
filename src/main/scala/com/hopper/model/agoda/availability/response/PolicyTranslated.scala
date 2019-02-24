package com.hopper.model.agoda.availability.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement, XmlValue}

@XmlRootElement(name = "PolicyTranslated")
@XmlAccessorType(XmlAccessType.NONE)
class PolicyTranslated
{
    @XmlAttribute(name = "language")
    var language: String = _

    @XmlValue
    var content: String = _
}

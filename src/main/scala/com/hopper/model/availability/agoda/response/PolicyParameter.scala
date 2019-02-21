package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement, XmlValue}

@XmlRootElement(name = "PolicyParameter")
@XmlAccessorType(XmlAccessType.NONE)
class PolicyParameter
{
    @XmlAttribute
    var charge: String = _

    @XmlAttribute
    var days: Int = _

    @XmlValue
    var content: String = _
}
package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAttribute, XmlAccessType, XmlAccessorType, XmlRootElement, XmlValue}

@XmlRootElement(name = "ErrorMessage")
@XmlAccessorType(XmlAccessType.NONE)
class ErrorMessage
{
    @XmlAttribute(name = "id")
    var exclusive: Double = _

    @XmlValue
    var message:String = _
}

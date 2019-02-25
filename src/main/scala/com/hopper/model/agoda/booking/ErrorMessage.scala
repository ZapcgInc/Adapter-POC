package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement, XmlValue}

@XmlRootElement(name = "ErrorMessage")
@XmlAccessorType(XmlAccessType.NONE)
class ErrorMessage
{
    @XmlAttribute(name = "id")
    var exclusive: Double = _

    @XmlValue
    var message:String = _
}

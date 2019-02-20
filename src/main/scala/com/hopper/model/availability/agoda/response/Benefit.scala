package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}

@XmlRootElement(name = "Benefit")
@XmlAccessorType(XmlAccessType.NONE)
class Benefit
{
    @XmlAttribute(name = "id")
    var id: String = _

    @XmlElement(name = "Name")
    var name: String = _

    @XmlElement(name = "Translation")
    var translation: String = _
}

package com.hopper.model.agoda.availability.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

@XmlRootElement(name = "PaxSettings")
@XmlAccessorType(XmlAccessType.NONE)
class PaxSettings
{
    @XmlAttribute(name = "submit")
    var submit :String =_

    @XmlAttribute(name = "infantage")
    var infantAge:Int = _

    @XmlAttribute(name = "childage")
    var childAge:Int = _
}

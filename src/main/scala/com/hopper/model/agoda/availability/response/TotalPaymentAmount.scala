package com.hopper.model.agoda.availability.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

@XmlRootElement(name = "TotalPaymentAmount")
@XmlAccessorType(XmlAccessType.NONE)
class TotalPaymentAmount
{
    @XmlAttribute(name = "inclusive")
    var inclusive:Double = _

    @XmlAttribute(name = "fees")
    var fees:Double = _

    @XmlAttribute(name = "exclusive")
    var exclusive:Double = _

    @XmlAttribute(name = "tax")
    var tax:Double = _
}
package com.hopper.model.agoda.booking

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

    def this(p_inclusive: Double, p_fees: Double, p_exclusive: Double, p_tax: Double)
    {
        this()
        inclusive = p_inclusive
        fees = p_fees
        exclusive = p_exclusive
        tax = p_tax
    }

    def this(shopRate : com.hopper.model.agoda.availability.response.Rate)
    {
        this()
        inclusive = shopRate.inclusive
        fees = shopRate.fees
        exclusive = shopRate.exclusive
        tax = shopRate.tax
    }
}
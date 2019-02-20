package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement(name = "RateInfo")
@XmlAccessorType(XmlAccessType.NONE)
class RateInfo
{
    @XmlElement(name = "Rate")
    var rate:Rate = _

    @XmlElement(name = "Included")
    var included:String = _

    @XmlElement(name = "TotalPaymentAmount")
    var totalPaymentAmount:TotalPaymentAmount = _

    @XmlElement(name = "Excluded")
    var excluded:String = _

    @XmlElement(name = "PromotionType")
    var promotionType:PromotionType = _
}

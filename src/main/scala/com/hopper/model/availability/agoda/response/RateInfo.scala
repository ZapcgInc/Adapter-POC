package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}
import javax.xml.bind.annotation.XmlElementWrapper

@XmlRootElement(name = "RateInfo")
@XmlAccessorType(XmlAccessType.NONE)
class RateInfo
{
    @XmlElement(name = "Rate")
    var rate:Rate = _

    @XmlElement(name = "Included")
    var included:String = _

    @XmlElementWrapper(name = "Surcharges")
    @XmlElement(name = "Surcharge")
    var surcharges:java.util.List[Surcharge]= _

    @XmlElement(name = "TotalPaymentAmount")
    var totalPaymentAmount:TotalPaymentAmount = _

    @XmlElement(name = "Excluded")
    var excluded:String = _

    @XmlElement(name = "PromotionType")
    var promotionType:PromotionType = _



    override def toString = s"RateInfo($rate, $included, $totalPaymentAmount, $excluded, $promotionType, $surcharges)"
}

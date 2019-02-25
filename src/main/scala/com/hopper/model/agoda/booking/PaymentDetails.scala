package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement(name = "PaymentDetails")
@XmlAccessorType(XmlAccessType.FIELD)
class PaymentDetails
{
    @XmlElement(name = "CreditCardInfo")
    var creditCardInfo: CreditCard = new CreditCard
}

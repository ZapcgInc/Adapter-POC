package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement(name = "CreditCardInfo")
@XmlAccessorType(XmlAccessType.FIELD)
class CreditCard
{
    @XmlElement(name = "Cardtype")
    val cardType: String = "MasterCard"

    @XmlElement(name = "Number")
    val number: String = "2222990905257051"

    @XmlElement(name = "ExpiryDate")
    val expiryDate: String = "092019"

    @XmlElement(name = "Cvc")
    val cvc: String = "123"

    @XmlElement(name = "HolderName")
    val holderName: String = "Joe Hoe"

    @XmlElement(name = "CountryOfIssue")
    val countryOfIssue: String = "TH"

    @XmlElement(name = "IssuingBank")
    val IssueBank: String = "Siam Commercial Bank"
}

package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement(name = "CustomerDetail")
@XmlAccessorType(XmlAccessType.FIELD)
class Phone
{
    @XmlElement(name = "CountryCode")
    var countryCode: String = _

    @XmlElement(name = "AreaCode")
    var areaCode: String = _

    @XmlElement(name = "Number")
    var number: String = _
}

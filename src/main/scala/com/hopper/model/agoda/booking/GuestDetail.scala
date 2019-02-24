package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}

@XmlRootElement(name = "GuestDetail")
@XmlAccessorType(XmlAccessType.FIELD)
class GuestDetail
{
    @XmlAttribute(name = "Primary")
    val primary: Boolean = false

    @XmlElement(name = "Title")
    var title: String = _

    @XmlElement(name = "FirstName")
    var firstName: String = _

    @XmlElement(name = "LastName")
    var lastName: String = _

    @XmlElement(name = "CountryOfPassport")
    var countryOfPassport: String = _

    @XmlElement(name = "Gender")
    var gender: String = _

    @XmlElement(name = "Age")
    var age: Int = _
}

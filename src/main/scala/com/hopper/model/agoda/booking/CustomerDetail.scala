package com.hopper.model.agoda.booking

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement(name = "CustomerDetail")
@XmlAccessorType(XmlAccessType.FIELD)
class CustomerDetail
{
    @XmlElement(name = "Language")
    var language: String = _

    @XmlElement(name = "Title")
    var title: String = _

    @XmlElement(name = "FirstName")
    var firstName: String = _

    @XmlElement(name = "LastName")
    var lastName: String = _

    @XmlElement(name="Phone")
    var phone: Phone = _

    @XmlElement(name = "Newsletter")
    var newsletter:Boolean = false

    @XmlElement(name = "IpAddress")
    var ipAddress:String = _
}

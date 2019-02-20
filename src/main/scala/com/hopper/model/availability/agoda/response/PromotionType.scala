package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement(name = "PromotionType")
@XmlAccessorType(XmlAccessType.NONE)
class PromotionType
{
    @XmlElement(name = "id")
    var id :String = _

    @XmlElement(name = "text")
    var text:String = _
}

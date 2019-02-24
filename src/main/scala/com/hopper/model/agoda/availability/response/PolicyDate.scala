package com.hopper.model.agoda.availability.response

import javax.annotation.Nullable
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}

@XmlRootElement(name = "PolicyDate")
@XmlAccessorType(XmlAccessType.NONE)
class PolicyDate
{
    @Nullable
    @XmlAttribute(name = "before", required = false)
    var before: String = _

    @Nullable
    @XmlAttribute(name = "after", required = false)
    var after: String = _

    @XmlElement(name = "Rate")
    var rate: Rate = _
}
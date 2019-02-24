package com.hopper.model.agoda.availability.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}

@XmlRootElement(name = "Room")
@XmlAccessorType(XmlAccessType.NONE)
class Room
{
    @XmlAttribute(name = "id")
    var id: String = _

    @XmlAttribute(name = "name")
    var name: String = _

    @XmlAttribute(name = "lineitemid")
    var lineItemID: Int = _

    @XmlAttribute(name = "rateplan")
    var ratePlan: String = _

    @XmlAttribute(name = "ratetype")
    var rateType: String = _

    @XmlAttribute(name = "currency")
    var currency: String = _

    @XmlAttribute(name = "model")
    var model: String = _

    @XmlAttribute(name = "ratecategoryid")
    var rateCategoryID: String = _

    @XmlAttribute(name = "blockid")
    var blockID: String = _

    @XmlElement(name = "StandardTranslation")
    var standardTranslation: String = _

    @XmlElement(name = "MaxRoomOccupancy")
    var maxRoomOccupancy: MaxRoomOccupancy = _

    @XmlElement(name = "RemainingRooms")
    var remainingRooms: Int = _

    @XmlElement(name = "RateInfo")
    var rateInfo: RateInfo = _

    @XmlElement(name = "Cancellation")
    var cancellation: Cancellation = _

    @XmlElement(name = "Benefits")
    var benefits: Array[Benefit] = _


    override def toString = s"Room(id=$id, name=$name, lineItemID=$lineItemID, ratePlan=$ratePlan, rateType=$rateType, currency=$currency, model=$model, rateCategoryID=$rateCategoryID, blockID=$blockID, standardTranslation=$standardTranslation, maxRoomOccupancy=$maxRoomOccupancy, remainingRooms=$remainingRooms, rateInfo=$rateInfo, cancellation=$cancellation, benefits=$benefits)"
}

package com.hopper.model.agoda.booking

import com.hopper.model.agoda.availability.response.{Rate, Surcharge}
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlElementWrapper, XmlRootElement}

@XmlRootElement(name = "BookingRequestV3")
@XmlAccessorType(XmlAccessType.FIELD)
class Room
{
    @XmlAttribute(name = "id")
    var id: String = _

    @XmlAttribute(name = "promotionid")
    var promotionid:String = _

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

    @XmlAttribute(name = "booknowpaylaterdate")
    var bookNowPayLaterDate: String = _

    @XmlElement(name = "Rooms")
    var roomCount: Int = _

    @XmlAttribute(name = "Adults")
    var adultsCount: Int = _

    @XmlAttribute(name = "Children")
    var childrenCount: Int = _

    @XmlElementWrapper(name = "ChildrenAges")
    @XmlElement(name = "Age")
    var childrenAges: Array[Int] = _

    @XmlAttribute(name = "Rate")
    var rate:Rate = _

    @XmlElementWrapper(name = "Surcharges")
    @XmlElement(name = "Surcharge")
    var surcharges:Array[Surcharge]= _

    @XmlElementWrapper(name = "GuestDetails")
    @XmlElement(name = "GuestDetail")
    var guestDetail: Array[GuestDetail] = _
}

package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, Node}


/*
@XmlAttribute(name = "id") var id: String = _
@XmlAttribute(name = "name") var name: String = _
@XmlAttribute(name = "lineitemid") var lineItemID: Int = _
@XmlAttribute(name = "rateplan")var ratePlan: String = _
@XmlAttribute(name = "ratetype")var rateType: String = _
@XmlAttribute(name = "currency")var currency: String = _
@XmlAttribute(name = "model")var model: String = _
@XmlAttribute(name = "ratecategoryid")var rateCategoryID: String = _
@XmlAttribute(name = "blockid")var blockID: String = _
@XmlElement(name = "StandardTranslation")var standardTranslation: String = _
@XmlElement(name = "MaxRoomOccupancy")var maxRoomOccupancy: MaxRoomOccupancy = _
@XmlElement(name = "RemainingRooms")var remainingRooms: Int = _
@XmlElement(name = "RateInfo")var rateInfo: RateInfo = _
@XmlElement(name = "Cancellation")var cancellation: Cancellation = _
@XmlElement(name = "Benefits")var benefits: Array[Benefit] = _
 */
case class Room(id:String, name : String, lineItemId: Int, ratePlan:String, rateType:String, currency:String, model:String,
                rateCategoryId:String, bockId:String, standardTranslation:String, maxRoomOccupancy:MaxRoomOccupancy,
                remainingRooms:Int, rateInfo:RateInfo, cancellation:Cancellation, benefits : List[Benefit])

object Room extends XmlSupport[Room] {


  override def fromXml(node: Node): Room = {



  }

  override def toXml(any: Room): Elem = ???


}


class RoomBuilder {

  var id:String = _
  var name : String = _
  var lineItemId: Int= _
  var ratePlan:String= _
  var rateType:String= _
  var currency:String= _
  var model:String= _
  var rateCategoryId:String= _
  var bockId:String= _
  var standardTranslation:String= _
  var maxRoomOccupancy:MaxRoomOccupancy= _
  var remainingRooms:Int= _
  var rateInfo:RateInfo= _
  var cancellation:Cancellation= _
  var benefits : ArrayBuffer[Benefit]= new ArrayBuffer()

  def withId(id:String)={
    this.id = id
    this
  }

  def withName(name:String) = {
    this.name = name
    this
  }

  def withLineItemId(lineItemId : Int) = {
    this.lineItemId= lineItemId
    this
  }

  def ratePlan(ratePlan)

  def build() = {

  }

}

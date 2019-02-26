package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, Node}


case class AvailabilityRequest(siteId: String, apiKey: String, requestType: Option[Int], id: String, propertyIds: String,
                               checkInDate: String, checkOutDate:String, roomCount : Int, adultCount:Int,
                               childrenAges:List[Int], language:String, currency:String, occypancy : List[String])

object AvailabilityRequest extends XmlSupport[AvailabilityRequest] {

  override def fromXml(node: Node): AvailabilityRequest = {
    val siteId: String = (node \@  "siteId")
    val apiKey = (node \@ "apiKey")
    val `type` = (node \ "Type")
    val id = (node \ "Id")
    val checkIn = (node \ "CheckIn")
    val checkout = (node \ "CheckOut")
    val rooms = (node \ "Rooms")
    val adults = (node \ "Adults")
    val childrenCount = (node \ "Children")
    val ages = (node \\ "ChildrenAges")
    new AvailabityRequestBuilder().
      withSiteId(siteId).
      build

  }

  override def toXml(ar: AvailabilityRequest): Elem = {
    <AvailabilityRequestV2 siteId={ar.siteId} apiKey={ar.apiKey}>
      {if (ar.requestType.isDefined) {<Type>{ar.requestType.get}</Type>}}
      <Id>{ar.propertyIds}</Id>
      <CheckIn>{ar.checkInDate}</CheckIn>
      <CheckOut>{ar.checkOutDate}</CheckOut>
      <Rooms>{ar.roomCount}</Rooms>
      <Adults>{ar.adultCount}</Adults>
      {
        if(!ar.childrenAges.isEmpty){
          <Children>{ar.childrenAges.size}</Children>
          <ChildrenAges>
          {ar.childrenAges.map{age => <Age>{age}</Age>}}
          </ChildrenAges>
        } else {
          <Children>0</Children>
        }
      }
      <Language>{ar.language}</Language>
      <Currency>{ar.currency}</Currency>
    </AvailabilityRequestV2>
  }

}


class AvailabityRequestBuilder(){

  var siteId: String = _
  var apiKey: String = _
  var requestType: Option[Int] = None
  var id: String = _
  var propertyIds: String = _
  var checkInDate: String = _
  var checkOutDate:String = _
  var roomCount : Int = 0
  var adultCount:Int = 0
  var childrenAges:List[Int] = Nil
  var language:String = _
  var currency:String = _
  var occupancy : ArrayBuffer[String] = ArrayBuffer()


  def withSiteId(siteId:String) = {
    this.siteId = siteId
    this
  }

  def withApiKey(apiKey:String) = {
    this.apiKey = apiKey
    this
  }
  def withRequestType(requestType : Int) = {
    this.requestType = Option(requestType)
    this
  }


  def build : AvailabilityRequest = {
    AvailabilityRequest(siteId, apiKey, requestType,id,propertyIds,checkInDate,checkOutDate,roomCount,adultCount,childrenAges,language, currency, occupancy.toList)
  }

}
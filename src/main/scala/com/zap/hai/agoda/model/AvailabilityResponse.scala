package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, Node}

/*
 @XmlAttribute(name = "status")
    var status: Int = 0

    @XmlAttribute(name = "searchid")
    var searchID: String = _

    @XmlElementWrapper(name = "Hotels")
    @XmlElement(name = "Hotel")
    var hotels: Array[Hotel] = _

    @XmlElementWrapper(name = "ErrorMessages")
    @XmlElement(name = "ErrorMessage")
    var errors: Array[ErrorMessage] = _
 */

case class AvailabilityResponse(status:Int, searchId:String, hotels : List[Hotel], errors:List[ErrorMessage])

object AvailabilityResponse extends XmlSupport[AvailabilityResponse] {

  override def fromXml(node: Node): AvailabilityResponse = {
    val builder = new AvailabilityResponseBuilder()
    builder.withStatus((node \@  "status").toInt)
    builder.withSearchId((node \@  "searchid"))
    (node \\ "Hotels").map{n => builder.addHotel(Hotel.fromXml(n))}
    (node \\ "ErrorMessages").map{n => builder.addError(ErrorMessages.fromXml(n))}


    ???
  }

  override def toXml(any: AvailabilityResponse): Elem = ???

}


class AvailabilityResponseBuilder{

  var status:Int = _
  var searchId:String = _
  var hotels : ArrayBuffer[Hotel] = ArrayBuffer()
  var errors:ArrayBuffer[ErrorMessage] = ArrayBuffer()

  def withStatus(status:Int) = {
    this.status = status
    this
  }

  def withSearchId(searchId: String) = {
    this.searchId = searchId
    this
  }

  def withHotels(hotels : List[Hotel]) = {
    this.hotels ++ hotels
    this
  }

  def addHotel(hotel : Hotel)={
    hotels += hotel
    this
  }
  def withErrors(errors : List[ErrorMessage]) = {
    this.errors ++ errors
    this
  }
  def addError(error: ErrorMessage)={
    this.errors += error
    this
  }

  def build():AvailabilityResponse = {
    AvailabilityResponse(status,searchId, hotels.toList, errors.toList)
  }

}


package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class CheapestRoom(exclusive: Double, tax: Double, fees: Double, inclusive: Double)

object CheapestRoom extends XmlSupport[CheapestRoom] {

  override def fromXml(node: Node): CheapestRoom = {
    new CheapestRoomBuilder().
      withExclusive((node \@ "exclusive").toDouble).
      withTax((node \@ "tax").toDouble).
      withFees((node \@ "fees").toDouble).
      withInclusive((node \@ "inclusive").toDouble)
      .build()
  }

  override def toXml(any: CheapestRoom): Elem = ???

}

class CheapestRoomBuilder {

  var exclusive: Double = _
  var tax: Double = _
  var fees: Double = _
  var inclusive: Double = _

  def withExclusive(exclusive: Double) = {
    this.exclusive = exclusive
    this
  }

  def withTax(tax: Double) = {
    this.tax = tax
    this
  }

  def withFees(fees: Double) = {
    this.fees = fees
    this
  }

  def withInclusive(inclusive: Double) = {
    this.inclusive = inclusive
    this
  }

  def build() = {
    new CheapestRoom(exclusive, tax, fees, inclusive)
  }


}
package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}


object AvailabilityResponse extends XmlSupport[AvailabilityResponse] {

  override def fromXml(node: Node): AvailabilityResponse = ???

  override def toXml(any: AvailabilityResponse): Elem = ???

}

case class AvailabilityResponse()
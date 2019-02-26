package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport

import scala.xml.{Elem, Node}

case class ErrorMessage(exclusive : Double, message:String)

object ErrorMessages extends XmlSupport[ErrorMessage] {

  override def fromXml(node: Node): ErrorMessage = {
    val builder = new ErrorMessageBuilder()
    builder.withExclusive((node \@ "id").toDouble)
    builder.withMessage((node.text))
    builder.build()
  }

  override def toXml(any: ErrorMessage): Elem = ???

}


class ErrorMessageBuilder {

  var exclusive : Double = _
  var message:String = _

  def withExclusive(exclusive : Double) = {
    this.exclusive = exclusive
    this
  }

  def withMessage(message:String) = {
    this.message = message
    this
  }

  def build()={
    ErrorMessage(exclusive, message)
  }


}
package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport
import scala.xml.{Elem, Node}

case class PaxSettings(submit: String, infantAge: Int, childAge: Int)

object PaxSettings extends XmlSupport[PaxSettings] {

  override def fromXml(node: Node): PaxSettings = {
    new PaxSettingsBuilder().
      withSubmit(node \@ "submit").
      withInfantAge((node \@ "infantage").toInt).
      withChildAge((node \@ "childage").toInt).
      build()
  }

  override def toXml(any: PaxSettings): Elem = ???

}

class PaxSettingsBuilder {

  var submit: String = _
  var infantAge: Int = _
  var childAge: Int = _

  def withSubmit(submit: String) = {
    this.submit = submit
    this
  }

  def withInfantAge(infantAge: Int) = {
    this.infantAge = infantAge
    this
  }

  def withChildAge(childAge: Int) = {
    this.childAge = childAge
    this
  }

  def build() = PaxSettings(submit, infantAge, childAge)


}

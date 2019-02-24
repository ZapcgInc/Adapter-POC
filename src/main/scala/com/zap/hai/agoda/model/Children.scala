package com.zap.hai.agoda.model

import zap.framework.xml.XmlSupport
import scala.xml._

object Children extends XmlSupport[Children] {

  override def fromXml(node: Node): Children = {
    ???
  }

  override def toXml(c: Children): Elem = {

    <Children>

    </Children>


  }

}

case class Children(count:Int, ages:List[Int])

package zap.framework

import scala.xml.Elem

package object xml {

  val xmlFormatter = new scala.xml.PrettyPrinter(500, 2)

  implicit class ElemToPrettyXml(elem: Elem) {

    def pretty = xmlFormatter.format(elem)

    def plain = elem.toString
  }


}

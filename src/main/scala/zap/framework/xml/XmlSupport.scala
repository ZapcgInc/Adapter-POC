package zap.framework.xml



trait XmlSupport[T] {

  def fromXml(node: scala.xml.Node) : T
  def toXml(any:T) : scala.xml.Elem


}

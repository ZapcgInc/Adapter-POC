package com.hopper.model.availability.agoda.response

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement}

@XmlRootElement(name = "Surcharge")
@XmlAccessorType(XmlAccessType.FIELD)
class Surcharge {

  @XmlAttribute(name = "id")
  var id:String = _
  @XmlAttribute(name ="method")
  var method:String = _
  @XmlAttribute(name = "charge")
  var charge:String = _
  @XmlAttribute(name ="margin")
  var margin:String =_
  @XmlElement (name = "Name")
  var name:String =_
  @XmlElement(name = "Rate")
  var rate:Rate = _


  override def toString = s"Surcharge($id, $method, $margin, $charge, $name, $rate)"
}

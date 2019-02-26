package com.zap.hai.agoda.xml

import com.zap.hai.agoda.model._
import zap.framework.xml._
import org.scalatest._

class AvailabilityRequestUnitTest extends FunSuite {

  test("Object to Xml"){
    val ar = AvailabilityRequest("siteId","apiKey",Option(1),"id","1,2,3","mm/dd/yyyy","mm1/dd1/yyyy1",1,2,List(1,2,3),"lang","curr",List())
    val xml = ar.toXml.pretty
    println(xml)

    val returned = xml.toAvailabilityRequest
    println(returned)


  }

}

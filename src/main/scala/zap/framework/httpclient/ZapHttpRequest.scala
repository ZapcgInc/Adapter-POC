package zap.framework.httpclient

import scala.collection.mutable.ArrayBuffer

sealed trait ZapHttpMethod
case object Get extends ZapHttpMethod
case object Post extends ZapHttpMethod
case object Put extends ZapHttpMethod

sealed trait ZapHttpEntity
case class ZapHttpStringEntity(content:String) extends ZapHttpEntity
case class ZapHttpFormParamsEntity(formParams : Map[String,String]) extends ZapHttpEntity
case class ZapHttpByteArrayEntity(array:Array[Byte]) extends ZapHttpEntity

case class ZapHttpRequest(method:ZapHttpMethod, url:String, body : Option[ZapHttpEntity], headers: (String,String)*)

case class UrlBuilder(baseUrl : String) {

  val queryParams : ArrayBuffer[(String,String)] = ArrayBuffer[(String,String)]()

  def queryParam(name:String, value:String)=  {

  }

  def build :String ={

    ???
  }
}
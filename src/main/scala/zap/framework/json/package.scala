package zap.framework

import java.lang.reflect.{ParameterizedType, Type}

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule


package object json {


  var customJson : Option[ObjectMapper] = None

  private def createJson() : ObjectMapper = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper
  }

  val json =  customJson.getOrElse(createJson())

  implicit class StringToObject(jsonString:String) {

    private[this] def typeFromManifest(m: Manifest[_]): Type = {
      if (m.typeArguments.isEmpty) { m.erasure }
      else new ParameterizedType {
        def getRawType = m.erasure
        def getActualTypeArguments = m.typeArguments.map(typeFromManifest).toArray
        def getOwnerType = null
      }
    }

    private[this] def typeReference[T: Manifest] = new TypeReference[T] {
      override def getType = typeFromManifest(manifest[T])
    }

    def parseJsonAs[T: Manifest]: T = {
      json.readValue(jsonString, typeReference[T])
    }

    def parseJsonAsEither[T: Manifest] : Either[Throwable,T] = {
      try {
        Right(json.readValue(jsonString, typeReference[T]))
      } catch {
        case t:Throwable => Left(t)
      }
    }

    def parseJsonAsOption[T: Manifest] : Option[T] = {
      try {
        Some(json.readValue(jsonString, typeReference[T]))
      } catch {
        case t:Throwable => None
      }
    }
  }

  implicit class ObjectToJsonString(any:Any){
    def toJsonPlain = json.writeValueAsString(any)
    def toJsonPretty = json.writerWithDefaultPrettyPrinter.writeValueAsString(any)
  }



}

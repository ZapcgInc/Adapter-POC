package zap.framework

import io.circe.{Encoder, Json}
import org.scalatest._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import com.fasterxml.jackson.annotation.JsonProperty

class CirceUnitTest extends FunSuite {


  test("test object to json string"){
    val json = Point("x","y").asJson.noSpaces
    println(json)
    val p = decode[Point](json)
    println(p)
  }

  test("test object to json string jackson"){
    import zap.framework.json._
    val json = Point("x","y").toJsonPlain
    val p = json.parseJsonAs[Point]

    println(json)
    println(p)

  }

}


case class Point(@JsonProperty("xAnd") x:String, @JsonProperty("yAnd")y:String)

object Point {
  implicit val encodeFoo: Encoder[Point] = new Encoder[Point] {
    final def apply(a: Point): Json = Json.obj(
      ("xAnd", Json.fromString(a.x)),
      ("yAnd", Json.fromString(a.y))
    )
  }

  implicit val decodeFoo: Decoder[Point] = new Decoder[Point] {
    final def apply(c: HCursor): Decoder.Result[Point] =
      for {
        foo <- c.downField("xAnd").as[String]
        bar <- c.downField("yAnd").as[String]
      } yield {
        new Point(foo, bar)
      }
  }


}




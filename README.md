# Zap Framework

## zap.framework.httpclient
This package contains the httpclient abstraction. These classes are used to communicate with agoda rest api. or can be
used in general for making http api calls.  The `zap.framework.httpclient` provides an import & play experience with default,
but you can also customize the feature.

```scala
import zap.framework.httpclient._

//a simple http get request
val response = zttpClient.execute(GetReq("http://www.google.com"))

//with headers
val response = zttpClient.execute(GetReq("http://www.google.com"),("Accept","application/json"),("Authorization","mykeysecret"))

```


## zap.framework.json
This package provides an abstraction over the jackson scala module. The `zap.framework.json` provides an import & play experience with default,
but you can also customize the feature. You can use the jackson @JsonProperty on your case classes to customize field names.

```scala
import zap.framework.json._

case class Point(x:String,y:String)

//create a formatter json string
val json:String = Point("1","2").toJsonPretty

val p:Point = """{"x":"1","y":"2"}""".parseJsonAs[Point]

```


## zap.framework.props
This package provides an abstraction for reading properties in a unified and consistent manner from any location.  The
`zap.framework.props` provides an import and play experience with defaults that you can also customize.

```scala
import zap.framework.props._


zaperties.req[String]("my.key")  //throws exception if key  not found
zaperties.opt[String]("my.key")  //returns None if key not found
zaperties.get[String]("my.key","my.val") //returns my.val if key not found

```

## zap.framework.xml
This package provides a trait that you can extend to enable a consisten contract for xml ser/de. It also provides basic
xml formatting.  Ref https://github.com/scala/scala-xml/wiki/Getting-started







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
Other request type are supported


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
Properties can be read from env vars, sys props, property files like typesafe config, etc.  The precendence of property loading can also be controlled.


## zap.framework.xml
This package provides a trait that you can extend to enable a consisten contract for xml ser/de. It also provides basic
xml formatting.  Ref https://github.com/scala/scala-xml/wiki/Getting-started

# Adapter

The adapter follows a layered design, where the layer below is abstracted from the layer above, in both domain and technology.

## Finagle Layer
This layer deals with the details of the finagle technology. This is the only layer that is finagle aware. Layers below are
agnostic of finagle.  The finagle layer calls the Controller Layer

## Controller Layer
This layer abstract the http server logic and can be plugged into any Rest framework like finagle, Play2, etc. This layer is responsbile
for request input validation, content negogiation, http response creation.  This layer calls the service layer.

## Service Layer
This layer deals in the Rapid/EPS domain, its input are eps domain inputs and its output is eps domain output. Only this layer is aware of
the EPS domain. It is responsible for business logic and orchestration. Transformation of the Agoda domain to EPS Domain.
This layer calls the agoda rest api client sdk.

## Agoda Rest Api Client Layer
This layer is responsible for dealing with making http client calls to the Agoda service. The input to this layer is the
Agoda domain, and the output is Either Agoda domain or HttpResonse.  The framework http client used in this layer, will allow
us to mock out the http calls to Agoda and create a rich set of automated tests. That can test all possible success and
failure scenarios.  The Agoda domain is built with case classes.

## Transformation Layer
This layer is invoked by the service layer. It is responsible to transform the Agoda domain into EPS Domain.  This layer is only
aware of these two domains.

## HaiFactory
This class demonstrates one way of doing dependency injection in scala using scala features like traits and lazy vals.



1. hopper.framework

2. com.hopper.adapter.server, {finale, controller, services, eps}

3. com.hopper.agoda{models, transfomers, rest client.}

4. sbt docker:publish

6. select 2.1, get swagger-spec for eps

7. Add version to the Finagle url, /2.1/properties/availability

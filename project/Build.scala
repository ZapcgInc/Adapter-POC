package hai

import sbt._

object build {

  def test(list: sbt.ModuleID*): Seq[sbt.ModuleID] = list.map(_ % "test")

  def compile(list: sbt.ModuleID*): Seq[sbt.ModuleID] = list.map(_ % "compile")


  val twitter_server = "com.twitter" %% "twitter-server" % "19.1.0"
  val finch_core = "com.github.finagle" %% "finchx-core" % "0.27.0"
  val finch_circe = "com.github.finagle" %% "finchx-circe" % "0.27.0"
  val finch_libs = compile(twitter_server, finch_core, finch_circe)

  val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val slf4j = "org.slf4j" % "slf4j-api" % "1.7.26"
  val tssl = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  val logging_libs = compile(slf4j, logback, tssl)

  val junit = "junit" % "junit" % "4.11"
  val junitint = "com.novocode" % "junit-interface" % "0.11"
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.5"

  val circe_generic = "io.circe" %% "circe-generic" % "0.11.1"
  val circe_parse = "io.circe" %% "circe-parser" % "0.11.1"
  val circe_libs = compile(circe_generic, circe_parse)

  val cucumber_libs = test(
    "io.cucumber" %% "cucumber-scala" % "4.2.0",
    "io.cucumber" % "cucumber-junit" % "4.2.0"
  )

  val jackson_core = "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8"
  val jackson_scala = "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"

  val apacheHttpVersion = "4.5.6"
  val apacheHttpClient = "org.apache.httpcomponents" % "httpclient" % apacheHttpVersion

  val commons_io = "commons-io" % "commons-io" % "2.6"

  val scala_xml = "org.scala-lang.modules" %% "scala-xml" % "1.1.1"

  val deps = compile(jackson_scala, apacheHttpClient, commons_io, scala_xml) ++
    test(junit, junitint, scalatest) ++
    finch_libs ++ logging_libs ++ cucumber_libs ++ circe_libs


}
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

  val cucumber_libs = test(
    "io.cucumber" %% "cucumber-scala" % "4.2.0",
    "io.cucumber" % "cucumber-junit" % "4.2.0"
  )

  val deps = finch_libs ++ logging_libs ++ cucumber_libs ++
    test(junit, junitint)


}
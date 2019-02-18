name := "Adoga Adapter"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.20.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.7",
  "org.xerial.sbt" % "sbt-pack" % "0.10.1"
)
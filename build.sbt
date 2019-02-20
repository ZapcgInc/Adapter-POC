name := "Adoga Adapter"

version := "1.0.0"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.11" % "test",
  "com.twitter" %% "finagle-http" % "19.1.0",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.7",
  "com.twitter" %% "twitter-server" % "19.1.0",
  "com.github.finagle" %% "finchx-core"  % "0.27.0",
  "com.github.finagle" %% "finchx-circe"  % "0.27.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.google.guava" % "guava" % "19.0",
  "commons-collections" % "commons-collections" % "3.2.2",
  "io.netty" % "netty-codec-http" % "4.0.15.Final"
)

enablePlugins(JavaServerAppPackaging)


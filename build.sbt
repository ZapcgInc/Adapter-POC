
scalaVersion := "2.12.8"

libraryDependencies ++= hai.build.deps

enablePlugins(JavaServerAppPackaging)

lazy val `agoda-svc` = project.in(file("."))


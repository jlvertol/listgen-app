name := "listgen"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.apache.poi" % "poi" % "5.2.1",
  "org.apache.poi" % "poi-ooxml" % "5.2.1",
  "com.lihaoyi" %% "os-lib" % "0.6.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
)

assemblyJarName in assembly := "listgen.jar"
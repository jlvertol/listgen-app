name := "listgen"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.apache.poi" % "poi" % "3.17",
  "org.apache.poi" % "poi-ooxml" % "3.17",
  "com.lihaoyi" %% "os-lib" % "0.6.2",
  "org.scalatest" %% "scalatest" % "3.0.9" % "test",
)

assemblyJarName in assembly := "listgen.jar"
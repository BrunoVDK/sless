organization in ThisBuild := "cpl"
scalaVersion in ThisBuild := "2.12.4"
version in ThisBuild := "0.0.1"

name := "exercises"

scalacOptions in ThisBuild ++= Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-deprecation",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

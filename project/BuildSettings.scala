import Dependencies._
import sbt.Keys._
import sbt._

object BuildSettings {

  lazy val basicSettings = Seq(
    version := "1.0.1",
    homepage := Some(new URL("http://helloscala.com/")),
    organization := "com.helloscala",
    organizationHomepage := Some(new URL("http://team.helloscala.com/")),
    startYear := Some(2014),
    scalaVersion := "2.11.6",
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-unchecked",
      "-feature",
      "-deprecation"
    ),
    javacOptions := Seq(
      "-encoding", "utf8",
      "-Xlint:unchecked",
      "-Xlint:deprecation"
    ),
    resolvers ++= Seq(
      "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "releases" at "http://oss.sonatype.org/content/repositories/releases",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"),
    libraryDependencies ++= (
      __compile(_scalaModules) ++
        __compile(_slf4j) ++
        __compile(_logback) ++
        __compile(_scala) ++
        __test(_scalatest)),
    offline := true
  )

  lazy val noPublishing = Seq(
    publish :=(),
    publishLocal :=()
  )

}


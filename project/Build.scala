import play.PlayImport._
import play._
import play.twirl.sbt.Import.TwirlKeys
import sbt.Keys._
import sbt._

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  ///////////////////////////////////////////////////////////////
  // helloscala project
  ///////////////////////////////////////////////////////////////
  lazy val helloscala = Project("helloscala", file("."))
    .enablePlugins(PlayScala)
    .aggregate(hsPlatform, hsCommon)
    .dependsOn(hsPlatform, hsCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hs-app",
      TwirlKeys.templateImports += "com.helloscala.site.model._",
      libraryDependencies ++= (
        __compile(cache) ++
          __compile(ws) ++
          __compile(_commonsEmail) ++
          __compile(_scalaLogging) ++
          __compile(_typesafeConfig)))

  ///////////////////////////////////////////////////////////////
  // projects
  ///////////////////////////////////////////////////////////////
  lazy val hsPlatform = Project("hs-platform", file("hs-platform"))
    .dependsOn(hsCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hs-platform",
      libraryDependencies ++= (
        __compile(_slick) ++
          __compile(_slickPg) ++
          __compile(_postgresql) ++
          __compile(_akkaActor)))

  lazy val hsCommon = Project("hs-common", file("hs-common"))
    .settings(basicSettings: _*)
    .settings(
      description := "hs-common",
      libraryDependencies ++= (
        __provided(_commonsEmail) ++
          __provided(_scalaLogging) ++
          __provided(_typesafeConfig)))

}


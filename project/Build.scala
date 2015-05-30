import _root_.sbt.Keys._
import _root_.sbt._
import play.sbt.Play.autoImport._
import play.sbt.PlayScala
import play.twirl.sbt.Import.TwirlKeys

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  ///////////////////////////////////////////////////////////////
  // helloscala project
  ///////////////////////////////////////////////////////////////
  lazy val helloscala = Project("helloscala-site", file("."))
    .enablePlugins(PlayScala)
    .aggregate(hsPlatform, hsCommon)
    .dependsOn(hsPlatform, hsCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hs-app",
      TwirlKeys.templateImports += "com.helloscala.site.data.model._",
      libraryDependencies ++= (
        __compile(cache) ++
          __compile(ws) ++
          __compile(_scala) ++
          __compile(_scalaModules) ++
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
        __provided(cache) ++
          __provided(ws) ++
          __compile(_slick) ++
          __compile(_slickPg) ++
          __compile(_postgresql) ++
          __compile(_hikariCP) ++
          __compile(_patchca) ++
          __compile(_akkaHttp) ++
          __compile(_akkaStream) ++
          __compile(_akkaActor)))

  lazy val hsCommon = Project("hs-common", file("hs-common"))
    .settings(basicSettings: _*)
    .settings(
      description := "hs-common",
      libraryDependencies ++= (
        __provided(_commonCodecs) ++
          __provided(_commonsLang3) ++
          __provided(_commonsEmail) ++
          __compile(_bson) ++
          __compile(_bouncycastle)))

}


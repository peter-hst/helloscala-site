import sbt._

object Dependencies {
  def __compile(dep: ModuleID): Seq[ModuleID] = __compile(Seq(dep))

  def __compile(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")

  def __provided(dep: ModuleID): Seq[ModuleID] = __provided(Seq(dep))

  def __provided(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "provided")

  def __test(dep: ModuleID): Seq[ModuleID] = __test(Seq(dep))

  def __test(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "test")

  def __runtime(dep: ModuleID): Seq[ModuleID] = __runtime(Seq(dep))

  def __runtime(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "runtime")

  def __container(dep: ModuleID): Seq[ModuleID] = __container(Seq(dep))

  def __container(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "container")

  val _scalaModules = Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"
  )

  private val verScala = "2.11.6"
  val _scala = Seq(
    "org.scala-lang" % "scala-library" % verScala,
    "org.scala-lang" % "scala-compiler" % verScala,
    "org.scala-lang" % "scala-reflect" % verScala
  )

  private val verAkka = "2.3.9"
  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaRemote = "com.typesafe.akka" %% "akka-remote" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka
  val _akkaTestkit = Seq(
    "com.typesafe.akka" %% "akka-testkit" % verAkka)

  val verAkkaStream = "1.0-M4"
  val _akkaStream = "com.typesafe.akka" % "akka-stream-experimental_2.11" % verAkkaStream

  val verAakkaHttp = "1.0-M4"
  val _akkaHttp = Seq(
    "com.typesafe.akka" %% "akka-http-experimental" % verAakkaHttp,
    "com.typesafe.akka" %% "akka-http-core-experimental" % verAakkaHttp
  )

  val slickVersion = "2.1.0"
  val _slick = "com.typesafe.slick" %% "slick" % slickVersion
  val _slickExtensions = "com.typesafe.slick" %% "slick-extensions" % slickVersion

  val verSlickPg = "0.6.5.3"
  val _slickPg = Seq(
    "com.github.tminglei" %% "slick-pg_joda-time" % verSlickPg,
    ("com.github.tminglei" %% "slick-pg_play-json" % verSlickPg).exclude("com.typesafe.play", "play-json"),
    "com.github.tminglei" %% "slick-pg" % verSlickPg
  )

  val _htmlUnit = "net.sourceforge.htmlunit" % "htmlunit" % "2.15"

  val _scalatest = Seq(
    "org.scalatest" %% "scalatest" % "2.2.4"
  )

  val verSelenium = "2.45.0"
  val _selenium = "org.seleniumhq.selenium" % "selenium-java" % verSelenium

  val verJetty = "9.2.10.v20150310"
  val _jetty = Seq(
    "org.eclipse.jetty" % "jetty-webapp" % verJetty
  )

  val verJson4s = "3.2.11"
  val _json4s = Seq(
    "org.json4s" %% "json4s-mongo" % verJson4s,
    "org.json4s" %% "json4s-ext" % verJson4s,
    "org.json4s" %% "json4s-jackson" % verJson4s)

  //val _bson = "org.mongodb" % "bson" % "3.0.0-rc0"

  val _casbah = "org.mongodb" %% "casbah" % "2.8.0"

  val _typesafeConfig = "com.typesafe" % "config" % "1.2.1"

  val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.1.0").exclude("org.scala-lang", "scala-library").exclude("org.scala-lang", "scala-reflect")

  val _netty = "io.netty" % "netty" % "4.0.26.Final"

  val _slf4j = "org.slf4j" % "slf4j-api" % "1.7.10"

  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.2"

  val _bouncycastle = "org.bouncycastle" % "bcprov-ext-jdk15on" % "1.50"

  val _javaxActivation = "javax.activation" % "activation" % "1.1.1"

  val _javaxJta = "javax.transaction" % "jta" % "1.1"

  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.3.3"

  val _commonsIo = "commons-io" % "commons-io" % "2.4"

  val _commonsImage = "org.apache.commons" % "commons-imaging" % "1.0-SNAPSHOT"

  val _commonsNet = "commons-net" % "commons-net" % "3.3"

  val _h2 = "com.h2database" % "h2" % "1.4.186"

  val _postgresql = "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

  val _jsoup = "org.jsoup" % "jsoup" % "1.8.1"

  val _asyncHttpClient = "com.ning" % "async-http-client" % "1.9.15"

  val verUndertow = "1.1.3.Final"
  val _undertowCore = "io.undertow" % "undertow-core" % verUndertow

  val _jodatime = "joda-time" % "joda-time" % "2.7"
}

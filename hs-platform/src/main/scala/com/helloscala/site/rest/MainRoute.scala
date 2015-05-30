package com.helloscala.site.rest

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorFlowMaterializer

import scala.concurrent.duration.Duration

object MainRoute {
  implicit val system = ActorSystem()
  implicit val materializer = ActorFlowMaterializer()

  import system.dispatcher

  val route =
    get {
      pathSingleSlash {
        complete {
          <html>
            <body>Hello world!</body>
          </html>
        }
      } ~
        path("ping") {
          complete("PONG!")
        } ~
        path("crash") {
          system.scheduler.scheduleOnce(Duration(2, TimeUnit.SECONDS))(system.shutdown())
          sys.error("BOOM!")
        }
    }

  def main(args: Array[String]): Unit = {
    val bindingFuture = Http().bindAndHandle(route, "localhost", 9999)
    bindingFuture.onComplete(println)
  }
}

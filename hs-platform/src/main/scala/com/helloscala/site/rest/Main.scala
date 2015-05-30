//package com.helloscala.site.rest
//
//import java.util.concurrent.TimeUnit
//
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.HttpMethods._
//import akka.http.scaladsl.model._
//import akka.stream.ActorFlowMaterializer
//import akka.stream.scaladsl.Sink
//
//import scala.concurrent.duration.Duration
//
//object Main {
//  implicit val system = ActorSystem()
//  implicit val materializer = ActorFlowMaterializer()
//
//  import system.dispatcher
//
//  def main(args: Array[String]): Unit = {
////    val requestHandler: HttpRequest => HttpResponse = {
////      case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
////        HttpResponse(entity = HttpEntity(MediaTypes.`text/html`, "<html><body>Hello world!</body></html>"))
////
////      case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
////        HttpResponse(entity = "PONG!")
////
////      case HttpRequest(GET, Uri.Path("/crash"), _, _, _) =>
////        system.scheduler.scheduleOnce(Duration(2, TimeUnit.SECONDS))(system.shutdown())
////        sys.error("BOOM!")
////
////      case _: HttpRequest =>
////        HttpResponse(404, entity = "Unknown resource!")
////    }
////
////    val serverSource = Http().bind("localhost", 9999)
////    val bindingFuture = serverSource.to(Sink.foreach { conn =>
////      println("Accepted new connection from " + conn.remoteAddress)
////      conn handleWithSyncHandler requestHandler
////    }).run()
////
////    bindingFuture.onComplete(entry => println("bindingFuture.onComplete: " + entry))
//  }
//}

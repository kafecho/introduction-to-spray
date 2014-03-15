package org.kafecho.learning.jozispray

import spray.http._
import spray.client.pipelining._
import akka.actor.ActorSystem
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure
import scala.annotation.tailrec
import scala.concurrent.Await

object AsyncHttpClient extends App {
  implicit val system = ActorSystem()
  import system.dispatcher

  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

  val future = pipeline(Get("http://www.news24.com"))
  
  future.onComplete{
    case Success(response) => println (response.entity.data.asString)
    case Failure(ex) => System.err.println(s"An error occured while executing the Http request: $ex.")
  }
}
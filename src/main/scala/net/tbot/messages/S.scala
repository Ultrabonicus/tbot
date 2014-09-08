package net.tbot.messages

import scala.concurrent.Future
import spray.http.HttpResponse

//master only
case object Start
case class LogIn(name: String, password: String)
case object SprayStart
case class GetPage(url: String)

//server
case object Ready
case object LoggedIn
//general
case class Hello(world: String)
case class Page(response: Future[HttpResponse])
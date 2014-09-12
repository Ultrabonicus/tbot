package net.tbot.main

import akka.actor._
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.{ Success, Failure }
import akka.util.Timeout
import net.tbot.utils.Implicits._

object Master {
	def props = Props[Master]
	def name = "Master"
}

class Master extends Actor with ActorLogging {
	val system = context.system
	import system.dispatcher
	val tWebClient = context.actorOf(ClientSupervisor.props, ClientSupervisor.name)
	
	def receive: Receive = {
		case LogIn(name, password) => tWebClient ! LogIn(name, password)
		case GetPage(url) => tWebClient ! GetPage(url)
		case LoggedIn => tWebClient ! GetPage("")
		case LogInFailed(list) => log.warning("blabla log in failed")
		case Page(page) => page.onSuccess{case x => log.info(x.parsePage.toString)} 
	}
	
}
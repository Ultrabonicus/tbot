package net.tbot.main

import akka.actor._
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.{ Success, Failure }
import akka.util.Timeout
import net.tbot.utils.Implicits._
import scala.language.postfixOps
import spray.http.HttpResponse

/**
 *  Актор-cупервайзер
 */

object ClientSupervisor {
	def props = Props[ClientSupervisor]
	def name = "ClientSupervisor"
}

case object LoggedIn
case class LogInFailed(failures: List[String])
case class Page(response: Future[HttpResponse])

class ClientSupervisor extends Actor with ActorLogging {

//	implicit val timeout = Timeout(15 seconds)
	
	val system = context.system
	
	import system.dispatcher

	val webClient = context.actorOf(WebClient.props,WebClient.name)

	def receive = notLoggedIn

	val notLoggedIn: Receive = {
		case LogIn(name,password) =>	 webClient ! LogIn(name,password);  log.info("Client logging in") 
		case GetPage(url) => webClient ! GetPage(url)
		case Page(str) => context.parent ! Page(str)
		case LoggedIn => {
			this.context.parent ! LoggedIn
			context.become(loggedIn)
			log.info("Client logged in")
		}
		case LogInFailed(list) => {
			log.warning("Following problems at logining: " + list)
			context.parent ! LogInFailed(list)
		}
	}
	
	val loggedIn: Receive = {
		case GetPage(url) => webClient ! GetPage(url)
		case Page(str) => context.parent ! Page(str)
	}

}
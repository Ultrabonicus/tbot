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
	
	val commentStream = context.actorOf(CommentStreamReader.props, CommentStreamReader.name)
	val commentStream2 = context.actorOf(CommentStreamReader.props, CommentStreamReader.name + 2)
	val optionalStream = context.actorOf(CommentStreamReader.props, CommentStreamReader.name + 4)
	
	override def preStart = {
		system.scheduler.scheduleOnce(3.seconds, commentStream, AssignInt(20))
		system.scheduler.scheduleOnce(3.seconds, commentStream2, AssignInt(60))
		system.scheduler.scheduleOnce(90.seconds, optionalStream, AssignInt(20))
	}
	
	tWebClient ! Initiate("tabun.everypony.ru")

	def receive: Receive = {

		case LogIn(name, password) => tWebClient ! LogIn(name, password)
		case GetPage(url) => tWebClient ! GetPage(url)
		
		case Unsubscribe(ref,typ) => log.debug("Unubscribe at master") ; tWebClient ! Unsubscribe(ref,typ)
		case Subscribe(ref,typ) => log.debug("Subscribe at master") ; tWebClient ! Subscribe(ref,typ)
		case LoggedIn => log.info("logged in")
		case LogInFailed(list) => log.warning("blabla log in failed")
		case Page(page) => page.onComplete {
			case Success(x) => log.info(x.parsePage.toString)
			case Failure(e) => log.warning("Failure!")
		}
	}

}
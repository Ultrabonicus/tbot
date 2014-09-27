package net.tbot.main

import akka.actor._
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.{ Success, Failure }
import akka.util.Timeout
import net.tbot.utils.Implicits._
import scala.language.postfixOps
import spray.http.HttpResponse
import scala.collection.immutable.HashMap
import spray.httpx.unmarshalling.Deserialized
import net.tbot.utils.CommentStreamLoad
import spray.http.HttpEntity

/**
 *  Актор-cупервайзер
 */

object ClientSupervisor {
	def props = Props[ClientSupervisor]
	def name = "ClientSupervisor"
}

case object NotInitiated
case class Initiate(url: String)
case object Initiated
case object LoggedIn
case class LogInFailed(failures: List[String])
case class Page(response: Future[HttpResponse])
abstract class Published
case class CommentStreamFuture(f:Future[Deserialized[CommentStreamLoad]]) extends Published

abstract class Stream
case object CommentStream extends Stream

case class Subscribe(ref: ActorRef, typ: Stream)

class ClientSupervisor extends Actor with Stash with ActorLogging {

	//	implicit val timeout = Timeout(15 seconds)

	val system = context.system

	import system.dispatcher

	val webClient = context.actorOf(WebClient.props, WebClient.name)
	
	def receive = notInitiated(context.parent)

	def notInitiated(master: ActorRef): Receive = {
		case Initiate(str) =>
			webClient ! Initiate(str); context.become(notInitiated(sender))
		case Initiated =>
			log.debug("Initiated"); context.become(notLoggedIn(master)); master ! Initiated; unstashAll
		case Subscribe(ref,typ) => log.debug("sbscrbe at notinit")
		case _ => stash
	}

	def notLoggedIn(master: ActorRef): Receive = {
		case LogIn(name, password) =>
			webClient ! LogIn(name, password); log.debug("Client logging in")
		case GetPage(url) =>
			webClient ! GetPage(url); log.debug("Requested page: " + url)
		case Page(ftr) =>
			master ! Page(ftr); log.debug("sending page")
		case LoggedIn => {
			master ! LoggedIn
			context.become(loggedIn(master))
			unstashAll
			log.debug("Client logged in")
		}
		case LogInFailed(list) => {
			log.warning("Following problems at logining: " + list)
			master ! LogInFailed(list)
		}
		case _ => stash
	}

	var subscribes:HashMap[String, List[ActorRef]] = HashMap()
	
	def loggedIn(master: ActorRef): Receive = {
		case GetPage(url) =>
			webClient ! GetPage(url); log.debug("Requested page with login :" + url)
		case Page(ftr) => master ! Page(ftr); log.debug("sending page2")
		case Subscribe(ref,typ) => {
			log.debug("Subscribing actor with ref " + ref + " to " + typ)
			val streamName = typ match {
				case CommentStream => "CommentStream"
			}
		  	val nl = 
		  		if(!subscribes.contains(streamName)) List(ref)
		  		else subscribes.apply(streamName) ++ List(ref)
			subscribes = subscribes ++ HashMap(streamName -> nl)
			webClient ! Subscribe(ref,typ)
	}
		case _ => log.debug("unexpected message at client supervisor")
	}

}
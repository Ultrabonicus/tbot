package net.tbot.main

import akka.actor._
import scala.concurrent.duration._
import net.tbot.messages._
import scala.concurrent.Future
import scala.util.{ Success, Failure }
import akka.util.Timeout
import net.tbot.utils.Implicits._
import net.tbot.messages.SprayStart
import scala.language.postfixOps

class Master extends Actor with ActorLogging {

	implicit val timeout = Timeout(15 seconds)
	val system = context.system
	import system.dispatcher

	val webClient = system.actorOf(Props[WebClient])

	def receive = general

	val general: Receive = {
		case Start =>
			log.info("Received Start at master"); webClient ! SprayStart
		case Ready =>
			log.info("Client is ready for LogIn"); webClient ! LogIn("Milkie", "lsgovno")
		case LoggedIn =>
			log.info("Client logged in"); webClient ! GetPage("")
		case Page(str) => str.onComplete {
			case Success(t) => log.info(t.parsePage.get.toString())
			case Failure(e) => log.error(e.toString())
		}
	}

}
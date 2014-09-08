package net.tbot.main

import akka.actor._
import net.tbot.sprayClient.SprayWebClient
import net.tbot.messages._
import scala.util.{ Success, Failure }
import spray.http._
import net.tbot.utils.Implicits._
import spray.http.HttpHeaders._

class WebClient extends Actor with ActorLogging {

	val system = context.system
	import system.dispatcher
	val webClient = new SprayWebClient()(system)
	log.info("Client created")

	val defurl = "http://tabun.everypony.ru"

	var receive: Receive = unLoggedIn

	def unLoggedIn: Receive = {
		case SprayStart => start(context.sender)
		case LogIn(name, password) => logIn(context.sender, name, password)
	}

	def loggedIn: Receive = {
		case GetPage(url) => context.sender ! Page(webClient.get(defurl + url))
	}

	def logIn(sender: ActorRef, name: String, password: String) = {
		webClient.get("http://tabun.everypony.ru").map { res =>
			res.status match {
				case StatusCodes.OK => {
					log.info("Got 200, looking for slskey")
					log.info("Got this \n" + res.getslskey)
					val res2 = webClient.logInWithResponse(res, name, password).get
					res2.getCookie("key") match {
						case Some(x) =>
							webClient.setCookie(Seq(x)); log.info("Key set after login"); sender ! LoggedIn; context.become(loggedIn)
						case None => log.warning("Didn't got key cookie after login")
					}
				}
				case any => log.error("Got this " + any + " at looking for slskey")
			}
		}
	}

	def start(sender: ActorRef) = {
		webClient.get("http://tabun.everypony.ru").map { res =>
			res.status match {
				case StatusCodes.OK => {
					log.info("Got 200, looking for cookies")
					val cook = res.getCookies
					cook match {
						case x :: _ => cook.find(_.name == "PHPSESSID") match {
							case Some(x) =>
								log.info("Setting cookie " + cook.toSeq); webClient.setCookie(cook.toSeq); sender ! Ready; log.info("PHPSESSID set")
							case None => log.warning("Can't find PHPSESSID cookie")
						}
						case Nil => log.warning("Didn't got any cookie at start")
					}
				}
				case any => log.error("got " + any + " at start")
			}
		}
	}
}
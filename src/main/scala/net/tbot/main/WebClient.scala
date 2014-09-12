package net.tbot.main

import akka.actor._
import net.tbot.sprayClient.SprayWebClient
import scala.util.{ Success, Failure }
import spray.http._
import net.tbot.utils.Implicits._
import spray.http.HttpHeaders._

/**
 *  Актор-вебклиент
 */

case class GetPage(url: String)
case class LogIn(name: String, password: String)

object WebClient{
	def props = Props[WebClient]
	def name = "WebClient"
}
	

class WebClient extends Actor with ActorLogging {

	val system = context.system
	import system.dispatcher
	val webClient = new SprayWebClient()(system)
	log.info("Client created")

	
	/**
	 * TODO убрать урл куда-нибудь в файл
	 */
	val defurl = "http://tabun.everypony.ru"

	var receive: Receive = unLoggedIn

	/**
	 * @return Receive для состояния без логина
	 */
	
	def unLoggedIn: Receive = {
		case LogIn(name, password) => logIn(context.sender, name, password)
		case GetPage(url) => context.sender ! Page(webClient.get(defurl + url))
	}

	/**
	 * @return Receive для состояния в логине
	 */
	
	def loggedIn: Receive = {
		case GetPage(url) => context.sender ! Page(webClient.get(defurl + url))
	}

	/**
	 * TODO смержить 2 действия для логина ниже
	 */
	
	def logIn(sender: ActorRef, name: String, password: String) = {
		webClient.get("http://tabun.everypony.ru").map { res =>
			res.status match {
				case StatusCodes.OK => {
					log.info("Got 200, looking for slskey & cookies")
					res.getCookie("PHPSESSID") match {
						case Some(sessid) =>	{ log.info("Got this phpsessid " + sessid)
							res.getslskey match {
								case Success(slskey) => {log.info("Got this security key" + slskey)
									webClient.setCookie(Seq(sessid))
									val res2 = webClient.logInWithResponse(slskey, name, password)
									res2.status match {
										case StatusCodes.OK => {
											res2.getCookie("key") match {
												case Some(key) => {
													webClient.setCookie(Seq(key)) 
													log.info("Key set after login") 
													sender ! LoggedIn
													context.become(loggedIn)
												}
												case None => {
													log.warning("Didn't got key cookie after login"); 
													sender ! LogInFailed(List("No key after login"))
												}
											}
										}
										case any => log.warning("Got this " + any + " after login attempt");
											sender ! LogInFailed(List(any + " status code after login request"))
									}
								}
								case Failure(e) => sender ! LogInFailed(List("Security key not found"))
							}
						}
						case None => { 
							log.warning("No session id")
							res.getslskey match {
								case Success(_) => sender ! LogInFailed(List("No session id"))
								case Failure(e) => log.warning("And no security key"); 
									sender ! LogInFailed(List("Security key not found","No session id"))
							} 
						}
					}
				}
				case any => log.error("Got this " + any + " at first request"); 
					sender ! LogInFailed(List(any + " status code at first request"))
			}
		}
	}
}
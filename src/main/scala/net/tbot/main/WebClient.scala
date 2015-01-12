package net.tbot.main

import akka.actor._
import net.tbot.sprayClient.SprayWebClient
import scala.util.{ Success, Failure }
import spray.http._
import net.tbot.utils.Implicits._
import spray.http.HttpHeaders._
import scala.concurrent.duration._
import net.tbot.utils.CommentStreamLoad
import spray.httpx.unmarshalling._
import scala.util.Success
import scala.collection.immutable.HashMap
import net.tbot.utils.CommentStreamLoad

/**
 *  Актор-вебклиент
 */

case class PostWithKey(url: String)
case class GetPage(url: String)
case class LogIn(name: String, password: String)

object WebClient {
	def props = Props[WebClient]
	def name = "WebClient"
}

class WebClient extends Actor with ActorLogging {

	val system = context.system
	import system.dispatcher
	val webClient = new SprayWebClient(log)(system)
	log.debug("Client created")

	var receive: Receive = notInitiated

	def notInitiated: Receive = {
		case Initiate(url) =>
			webClient.setDefaultUrl(url);
			sender ! Initiated;
			context.become(notLoggedIn)
	}

	/**
	 * @return Receive для состояния без логина
	 */

	def notLoggedIn: Receive = {
		case LogIn(name, password) => logIn(context.sender, name, password)
		case GetPage(url) => sender ! Page(webClient.get(url))
	}

	/**
	 * @return Receive для состояния в логине
	 */
	
	def loggedIn(subscribes: HashMap[Stream, CancelAndSubscribers]): Receive = {
		case GetPage(url) => sender ! Page(webClient.get(url)); log.debug("taking page")
		case Subscribe(ref, typ) => subscribe(subscribes,ref,typ)
		case Unsubscribe(ref, typ) => unsubscribe(subscribes,ref,typ)
	}
	
	type Cancel = Either[ActorRef,Cancellable]
	type CancelAndSubscribers = (Cancel,List[ActorRef])
	
	// type CancelAndSubscribes это tuple (Either[ActorRef,Cancellable],List[ActorRef])
//	var subscribes:HashMap[Stream, CancelAndSubscribers] = HashMap()
	
	def doSubscribe(stream:Stream):Cancel = {
		stream match {
			case CommentStream => Right(scheduleCommentStream)
		}
	}
	
	def subscribe(subscribes:HashMap[Stream, CancelAndSubscribers], ref:ActorRef,typ:Stream) = {
			log.debug("Subscribing actor with ref " + ref + " to " + typ)
		  	val newCAS:CancelAndSubscribers = 
		  		if(!subscribes.contains(typ)){
		  			val canc = doSubscribe(typ)
		  			(canc ,List(ref))
		  		} else {
		  			val prev = subscribes.apply(typ)
		  			(prev._1, prev._2  ++ List(ref))
		  		}
			context.system.eventStream.subscribe(ref, typ.getClassOfFuture)
//			subscribes = subscribes ++ HashMap(typ -> newCAS)
			context.become(loggedIn(subscribes ++ HashMap(typ -> newCAS)))
	}
	
	def unsubscribe(subscribes:HashMap[Stream, CancelAndSubscribers], ref:ActorRef, typ:Stream) = {
		log.debug("Unsubscribing actor with ref " + ref + " from " + typ)
		if(subscribes.contains(typ)){
			val cas = subscribes.apply(typ)
			val nl = cas._2.filterNot(x => x == ref)
			if (!nl.isEmpty) {
				val newCAS = (cas._1, nl)
//				subscribes = subscribes ++ HashMap(typ -> newCAS)
				context.system.eventStream.unsubscribe(ref, typ.getClassOfFuture)
				log.debug("Unsubscribed ref: " + ref + " from stream: " + typ)
				context.become(loggedIn(subscribes ++ HashMap(typ -> newCAS)))
			} else {
				cas._1.fold(x => x ! Kill, x => x.cancel)
//				subscribes = subscribes.-(typ)
				context.system.eventStream.unsubscribe(ref, typ.getClassOfFuture)
				context.become(loggedIn(subscribes.-(typ)))
				log.debug("Unsubscribed ref: " + ref + " from stream: " + typ + " and killed stream")
			}
		} else {
			log.warning("Unsubscribing ref: " + ref + " from stream: " + typ + " that doesn't exists")
		}
			
	}
	
	def scheduleCommentStream = context.system.scheduler.schedule(10.seconds, 10.seconds){
		import net.tbot.utils.JsonProtocols._
		import spray.httpx.SprayJsonSupport._
		log.debug("requested at scheduler")
		webClient.postWithKey("/ajax/stream/comment/") match {
			case Success(res) => {
				context.system.eventStream.publish(event = CommentStreamFuture(res.map(_.entity.as[CommentStreamLoad])))
				log.debug("published at scheduler")
			}
			case Failure(e) => {log.debug("failure at creating request for CommentStream scheduler")}
		}
		
	}
	
	
	
	
	

	/**
	 * TODO ужас
	 */
	def logIn(sender: ActorRef, name: String, password: String) = {
		webClient.get().map { res =>
			res.status match {
				case StatusCodes.OK => {
					log.debug("Got 200, looking for slskey & cookies")
					res.getCookie("PHPSESSID") match {
						case Some(sessid) => {
							log.debug("Got this phpsessid " + sessid)
							res.getCookie("LIVESTREET_SECURITY_KEY") match {
								case Some(slskey) => {
									log.debug("Got this security key " + slskey)
									webClient.setCookie(Seq(sessid, slskey))
									val sekey = res.getslskey.get
									val res2 = webClient.logInWithResponse(name, password,sekey)
									res2.status match {
										case StatusCodes.OK => {
											res2.getCookie("key") match {
												case Some(key) => {
													webClient.setCookie(Seq(key))
													log.debug("Key " + key.value + " set after login")
													sender ! LoggedIn
													context.become(loggedIn(HashMap()))
												}
												case None => {
													log.warning("Didn't got key cookie after login: " + res2);
													sender ! LogInFailed(List("No key after login"))
												}
											}
										}
										case any =>
											log.warning("Got this " + any + " after login attempt");
											sender ! LogInFailed(List(any + " status code after login request"))
									}
								}
								case None => sender ! LogInFailed(List("Security key not found"))
							}
						}
						case None => {
							log.warning("No session id")
							res.getslskey match {
								case Success(_) => sender ! LogInFailed(List("No session id"))
								case Failure(e) =>
									log.warning("And no security key");
									sender ! LogInFailed(List("Security key not found", "No session id"))
							}
						}
					}
				}
				case any =>
					log.error("Got this " + any + " at first request");
					sender ! LogInFailed(List(any + " status code at first request"))
			}
		}
	}

}
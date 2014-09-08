package net.tbot.sprayClient

import scala.concurrent.{ Future, Promise, Await }
import scala.concurrent.duration._
import akka.actor.ActorSystem
import spray.http._
import spray.client.pipelining._
import spray.http.HttpHeaders._
import scala.xml.NodeSeq
import scala.util.{ Try, Success, Failure }
import net.tbot.utils.{ TLogin, NoSecurityKeyException }
import net.tbot.utils.Implicits._
import scala.language.postfixOps

trait GetPage {
	def get(url: String): Future[HttpResponse]
}

class SprayWebClient(implicit system: ActorSystem) extends GetPage {

	import system.dispatcher

	var pipeline: HttpRequest => Future[HttpResponse] = sendReceive

	private var cookies: Seq[HttpCookie] = Seq.empty

	def get(url: String) = {
		val uri = Uri(url)
		val request = Get(uri)
		val response = pipeline(request)
		response
	}

	def setCookie(cookie: Seq[HttpCookie]) = {
		cookies = cookies ++ cookie
		pipeline = addHeader(HttpHeaders.Cookie(cookie)) ~> sendReceive
	}

	def logInWithResponse(response: HttpResponse, login: String, password: String): Try[HttpResponse] = Try {
		val slskey = response.getslskey.getOrElse(throw NoSecurityKeyException)
		val res = pipeline(TLogin.loginRequest(login, password, slskey, true))
		Await.result(res, 5 seconds)
	}

}
package testServer

import akka.actor.Actor
import akka.util.Timeout
import scala.concurrent.duration._
import spray.can.Http
import spray.util._
import spray.http._
import HttpMethods._
import HttpHeaders._
import MediaTypes._
import StatusCodes._
import testFiles.TestPages
import spray.routing.Route
import spray.routing.HttpService
import net.tbot.utils.CustomUnmarshaller
import scala.xml.NodeSeq

class TestServer extends Actor with TestRouting {
	implicit val timeout: Timeout = 1.second

	import context.dispatcher

	def actorRefFactory = context

	val receive = runRoute(route)
}
trait TestRouting extends HttpService {

	def route = {
		get {
			pathSingleSlash {
				cookie("key") { cookie =>
					if (cookie.value.matches(".*29d9ec082249f533d01c2beb41d2d1ae*.")) complete(mainPageLogged)
					else complete(mainPageUnlogged)
				} ~ {
					complete(mainPageUnlogged)
				}
			}
		} ~
			post {
				path("login" / "ajax-login") {
					cookie("PHPSESSID") { cookie =>
						if (cookie.value.matches(".*d4d4qd9l8lpb32jjl5r63k68p6")) complete(successLoginResponse)
						else complete(failedLoginResponse)
					} ~ {
						complete(failedLoginResponse)
					}
				}
			}
	}

	val failedLoginResponse = {
		HttpResponse(entity = "fail")
	}

	lazy val successLoginResponse = {
		val headers = List(
			`Set-Cookie`(
				HttpCookie(
					name = "key",
					content = "29d9ec082249f533d01c2beb41d2d1ae")))
		HttpResponse(headers = headers)
	}

	def validate(cookie: HttpCookie, entity: HttpEntity): Boolean = {
		val phpsessid = cookie.value == "d4d4qd9l8lpb32jjl5r63k68p6"
		import scala.util.matching.Regex
		val xml: NodeSeq = CustomUnmarshaller.NodeSeqUnmarshaller(entity).right.get
		val str = xml.\("head").\("script")
		val pattern = new Regex("'\\w{32}'")
		val slskey = pattern.findFirstIn(str.toString).getOrElse(throw new NoSuchElementException).substring(1, 33).matches("f019ff29309714e3df1fd44cdd7fd6d2")
		phpsessid && slskey
	}

	lazy val mainPageUnlogged: HttpResponse = {
		val entity = TestPages.mainUnlogged
		val headers = List(
			Connection("Close"),
			`Set-Cookie`(HttpCookie(name = "PHPSESSID", content = "d4d4qd9l8lpb32jjl5r63k68p6")),
			`Set-Cookie`(HttpCookie(name = "COOKIE_SECURITY_KEY", content = "d4d4qd9l8lpb32jjl5r63k68p6")))
		val status = OK
		HttpResponse(status = status, headers = headers, entity = entity)
	}

	lazy val mainPageLogged: HttpResponse = {
		val entity = TestPages.mainLogged
		HttpResponse(status = OK, entity = entity)
	}

}
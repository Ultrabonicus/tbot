package net.tbot.utils

import spray.httpx.unmarshalling.Deserializer._
import spray.httpx.marshalling._
import spray.httpx.RequestBuilding._
import spray.http._
import HttpHeaders._
import HttpMethods._
import MediaTypes._
import scala.xml._
import scala.util._

case object NoSecurityKeyException extends Exception

object TLogin {
	import spray.util._
	def loginRequest(login: String, password: String, slskey: String, remember: Boolean = true): HttpRequest = {
		val data = collection.immutable.ListMap("login" -> login, "password" -> password, "remember" -> (if (remember) "on" else "off"), "return-path" -> "http://tabun.everypony.ru/", "security_ls_key" -> slskey)
		val request = Post("http://tabun.everypony.ru/login/ajax-login", FormData(data))
		request.withHeaders(List(RawHeader("X-Requested-With", "XMLHttpRequest")))
	}

}
package sbasicTest

import org.specs2._
import spray.http._
import HttpMethods._
import HttpHeaders._
import MediaTypes._
import net.tbot.utils.Implicits._
import org.specs2.specification.AllExpectations
import scala.util.Success
import scala.util.Failure
import testFiles.TestPages

class UtilsImplictsTest extends Specification with AllExpectations {
	def is =
		"In package net.tbot.utils.Implicits" ^
			"getCookies should collect cookies" ! e1 ^
			"getCookie should get cookie" ! e2 ^
			"getlskey should get security key" ! e3

	def e1 = {
		response.getCookies.exists(x => x.name == "PHPSESSID") &&
			response.getCookies.exists(x => x.name == "key")

	}

	def e2 = {
		(response.getCookie("PHPSESSID").get.toString must contain("d4d4qd9l8lpb32jjl5r63k68p6")) &&
			(response.getCookie("key").get.toString must contain("29d9ec082249f533d01c2beb41d2d1ae"))
	}

	def e3 = {
		response.getslskey match {
			case Success(x) => x == "f019ff29309714e3df1fd44cdd7fd6d2"
			case Failure(_) => false
		}
	}

	lazy val response = {
		val entity = TestPages.mainUnlogged
		val headers = List(
			`Set-Cookie`(HttpCookie(name = "PHPSESSID", content = "d4d4qd9l8lpb32jjl5r63k68p6")),
			`Set-Cookie`(HttpCookie(name = "key", content = "29d9ec082249f533d01c2beb41d2d1ae")))

		HttpResponse(entity = entity, headers = headers)

	}
}
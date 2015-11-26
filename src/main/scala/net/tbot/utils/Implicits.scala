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

object Implicits {

	implicit class ResponseMethods(response: HttpResponse) {

		/**
		 * Достаёт коллекцию с куками
		 *
		 * @return коллкция с куками
		 */

		def getCookies: Seq[HttpCookie] = {
			response.headers.collect { case `Set-Cookie`(x) => x }
		}

		/**
		 * Достаёт конкретную куку если такая есть
		 *
		 * @param name название куки
		 *
		 * @return Option с кукой
		 */

		def getCookie(name: String): Option[HttpCookie] = {
			response.getCookies.find(x => x.name == name)
		}

		/**
		 * Пытается распарсить ответ
		 *
		 * @return Try с результатом в формате XML
		 */

		def parsePage: Try[NodeSeq] = Try { CustomUnmarshaller.NodeSeqUnmarshaller(response.entity).right.get }

		/**
		 * Пытается достать livestreet_security_key
		 *
		 * return Try с результатом
		 */

		def getslskey: Try[String] = Try {
			import scala.util.matching.Regex
			val xml: NodeSeq = CustomUnmarshaller.NodeSeqUnmarshaller(response.entity).right.get
			val str = xml.\("head").\("script")
			val pattern = new Regex("'\\w{32}'")
			pattern.findFirstIn(str.toString).getOrElse(throw new NoSuchElementException).substring(1, 33)
		}
	}

}
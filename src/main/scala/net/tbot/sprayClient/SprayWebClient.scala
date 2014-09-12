package net.tbot.sprayClient

import scala.concurrent.{ Future, Promise, Await }
import scala.concurrent.duration._
import akka.actor.ActorSystem
import spray.http._
import spray.client.pipelining._
import spray.http.HttpHeaders._
import scala.xml.NodeSeq
import scala.util.{ Try, Success, Failure }
import net.tbot.utils.TLogin
import net.tbot.utils.Implicits._
import scala.language.postfixOps

trait GetPage {
	def get(url: String): Future[HttpResponse]
}
/**
 * Общий класс для создания веб-клиента
 */
class SprayWebClient(implicit system: ActorSystem) extends GetPage {
	
	import system.dispatcher

	private var pipeline: HttpRequest => Future[HttpResponse] = sendReceive

	private var cookies: Seq[HttpCookie] = Seq.empty

	/**
	 * GET запрос по ссылке
	 * 
	 *  @param url ссылка
	 *  
	 *  @return future с ответом сервера
	 */
	def get(url: String) = {
		val uri = Uri(url)
		val request = Get(uri)
		val response = pipeline(request)
		response
	}
	
	/**
	 * Добавляет куки ко всем запросам
	 * 
	 * @param cookie коллекция с куками
	 */
	def setCookie(cookie: Seq[HttpCookie]) = {
		cookies = cookies ++ cookie
		pipeline = addHeader(HttpHeaders.Cookie(cookie)) ~> sendReceive
	}
	/**
	 * Запрос на логин из ответа
	 * 
	 * TODO проверить ответ и бросить исключение в случае неудачи
	 * 
	 * @param slskey livestreet_security_key
	 * 
	 * @param login логин
	 * 
	 * @param password пароль
	 * 
	 * @return ответ
	 */
	def logInWithResponse(slskey: String, login: String, password: String): HttpResponse = {
		val res = pipeline(TLogin.loginRequest(login, password, slskey, true))
		Await.result(res, 15 seconds)
	}

}
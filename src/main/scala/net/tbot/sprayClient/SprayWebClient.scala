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
import akka.event.LoggingAdapter

trait GetPage {
	def get(url: String): Future[HttpResponse]
}
/**
 * Общий класс для создания веб-клиента
 */
class SprayWebClient(log:LoggingAdapter)(implicit system: ActorSystem) extends GetPage {

	import system.dispatcher

	private var pipeline: HttpRequest => Future[HttpResponse] = sendReceive

	private var cookies: Set[HttpCookie] = Set.empty

	private var defurl: String = ""
		
	private var skey: Option[String] = None

	/**
	 * Устанавливает хост по
	 */

	def setDefaultUrl(url: String) = {
		defurl = url
		pipeline = /*addHeader(HttpHeaders.Host(url)) ~>*/ sendReceive
	}

	/**
	 * GET запрос по ссылке
	 *
	 *  @param url ссылка
	 *
	 *  @return future с ответом сервера
	 */

	def get(url: String = "") = {
		val uri = Uri("http://" + defurl + url)
		val request = Get(uri)
		val response = pipeline(request)
		response
	}

	/**
	 * POST запрос по ссылке
	 *
	 *  @param url ссылка
	 *
	 *  @param data HttpEntity с данными для отправки
	 *
	 *  @return future с ответом сервера
	 */

	def post(url: String, data: HttpEntity) = {
		val uri = Uri("http://" + defurl + url)
		val request = Post(uri).withEntity(data)
		val response = pipeline(request)
		response
	}
	
	def postWithKey(url: String, formData:Map[String,String] = Map()) = Try{
		val uri = Uri("http://" + defurl + url)
		val data = Map("security_ls_key" -> skey.get) ++ formData
		val request = Post(uri,FormData(data))
		log.debug(request.toString)
		val response = pipeline(request.withHeaders(List(RawHeader("X-Requested-With", "XMLHttpRequest"))))
		response
	}

	/**
	 * Добавляет куки ко всем запросам
	 *
	 * @param cookie коллекция с куками
	 */
	def setCookie(cookie: Seq[HttpCookie]) = {
		cookies = cookies ++ cookie
		val cookiesSeq = cookies.toSeq
		pipeline =  addHeader(HttpHeaders.Cookie(cookiesSeq)) ~> sendReceive
	}
	/**
	 * Запрос на логин из ответа
	 *
	 * TODO проверить ответ и бросить исключение в случае неудачи
	 *
	 * @param login логин
	 *
	 * @param password пароль
	 *
	 * @return ответ
	 */
	def logInWithResponse(login: String, password: String, slskey: String): HttpResponse = {
		skey = Some(slskey)
		val res = pipeline(TLogin.loginRequest(login, password, slskey, true, "http://" + defurl))
		Await.result(res, 15 seconds)
	}

}
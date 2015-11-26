package net.tbot.utils
import scalaz._
import syntax.apply._, syntax.validation._

object Validate {
	def validate[A](value: => A, msg:Option[String] = None): ValidationNel[(Exception,Option[String]),A] = {
		try {
			value.success
		}
		catch {
			case ex: Exception => (ex,msg).failureNel
		}
	}
}
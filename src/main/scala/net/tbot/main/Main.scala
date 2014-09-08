import akka.actor._
import akka.pattern.{ ask, pipe }
import net.tbot.messages._
import net.tbot.main.Master

object main extends App {
	val system = ActorSystem("System")
	val master = system.actorOf(Props[Master])
	master ! Start

}
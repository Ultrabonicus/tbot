import akka.actor._
import akka.pattern.{ ask, pipe }
import net.tbot.main.Master
import net.tbot.main.LogIn
import scala.concurrent.duration._
import scala.language.postfixOps
import net.tbot.main.GetPage

object main extends App {
	val system = ActorSystem("System")
	val master = system.actorOf(Master.props, Master.name)
	master ! LogIn("Milkie", "lsgovno")
	Thread.sleep(10000)
}
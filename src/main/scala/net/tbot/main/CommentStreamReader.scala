package net.tbot.main

import akka.actor._
import net.tbot.utils.CommentStreamLoad
import scala.concurrent.duration._
import scala.util.{Success,Failure}

object CommentStreamReader{
	def props = Props[CommentStreamReader]
	def name = "CommentStreamReader"
}

case class AssignInt(int:Int)

class CommentStreamReader extends Actor with ActorLogging{
	
	val system = context.system
	val parent = context.parent
	var sc:Option[Cancellable] = None
	
	import system.dispatcher
	
	override def preStart = {
		log.debug("At pre start")
		log.debug(parent.path.toString())
	}
	
	def receive:Receive = {
		case AssignInt(int:Int) => context.system.scheduler.scheduleOnce(delay = int.seconds, receiver = parent, message = Unsubscribe(context.self, CommentStream));
				sc = Option.apply(context.system.scheduler.scheduleOnce(delay = 3.seconds, receiver = parent, message = Subscribe(context.self, CommentStream)))
		case CommentStreamFuture(f) => f.onComplete{
				case Success(res) => val res2 = res.right.get ; log.info(/*CommentStreamParser.fromTextString(res.right.get.sText).map(x => x.author).toString*/"Not invented here")
				case Failure(f) => log.warning(f.toString)
		}
		case any => log.info("unknown message: " + any)
	}

}
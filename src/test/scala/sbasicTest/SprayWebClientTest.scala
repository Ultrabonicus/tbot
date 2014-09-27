package sbasicTest

import akka.testkit.TestActorRef
import scala.concurrent.Await
import akka.pattern.ask
import akka.testkit.TestProbe
import akka.actor.ActorSystem
import akka.testkit.ImplicitSender
import akka.testkit.TestKit
import net.tbot.main.ClientSupervisor
import net.tbot.main.Initiate
import spray.testkit.Specs2RouteTest
import org.specs2.mutable.Specification
import org.specs2.specification.AllExpectations
import testServer.TestServer
import akka.actor.Props
import akka.io.IO
import spray.can.Http
import net.tbot.main.Initiate
import net.tbot.main.Initiated
import org.specs2.execute.AsResult
import org.specs2.time.NoTimeConversions
import scala.concurrent.duration._
import net.tbot.main.LogIn
import net.tbot.main.LoggedIn
import net.tbot.main.GetPage
import net.tbot.main.Page
import scala.util.Success
import scala.util.Failure
import org.specs2.specification.BaseSpecification
import org.specs2.specification.Fragments
import org.specs2.specification.Step
import org.specs2.mutable.SpecificationLike
import scala.util.Success
import org.specs2.specification.After
import org.specs2.mutable.Before
import org.specs2.specification.Scope

class WebClientTest extends TestKit(ActorSystem("ClientTest")) with SpecificationLike
	with NoTimeConversions with After with ImplicitSender {

	/*	override def map (fs: =>Fragments) = step(fs) ^ step(system.shutdown, global = true)
*/
	import cont._

	sequential ^
		"client should " ^ {
			"initiate properly" in {
				probe1.send(client, Initiate("localhost"))
				within(3.second)(
					probe1.receiveOne(max = 3.seconds) match {
						case Initiated => true
						case _ => false
					})
			}
			"then login properly" in {
				probe1.send(client, LogIn("Some", "Whatever"))
				within(3.second)(
					probe1.receiveOne(max = 3.second) match {
						case LoggedIn => true
						case _ => false
					})
			}
			"and receive page" in {
				probe1.send(client, GetPage("/"))
				within(3.second)(
					probe1.receiveOne(max = 3.second) match {
						case Page(fpg) => {
							Await.result(fpg.map(x => x.entity.asString.matches("(?s).*Milkie.*")), 3.seconds)
						}
						case _ => false
					})
			}
		}
	def after = shutdown(system)

	object cont {
		val handler = system.actorOf(Props[TestServer], name = "testServer")

		IO(Http) ! Http.Bind(handler, "localhost", 80)

		var probe1 = TestProbe()

		var client = system.actorOf(ClientSupervisor.props, ClientSupervisor.name)

	}
}
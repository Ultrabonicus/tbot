import wartremover._

wartremoverSettings

scalacOptions ++= Seq("-encoding", "utf8", "-deprecation", "-unchecked")

scalacOptions ++= Seq("-feature")

scalaVersion := "2.11.2"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
   "org.scalaz" %% "scalaz-core" % "7.1.0",
   "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
   "io.spray" %% "spray-client" % "1.3.1",
   "io.spray" %% "spray-can" % "1.3.1",
   "io.spray" %% "spray-routing" % "1.3.1",
   "io.spray" %% "spray-json" % "1.2.6",
   "com.typesafe.akka" %% "akka-actor" % "2.3.5",
   "com.typesafe.akka" %% "akka-slf4j" % "2.3.5",
   "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
   "org.specs2" %% "specs2" % "2.4.15" % "test",
   "com.typesafe.akka" %% "akka-testkit" % "2.3.5" % "test",
   "io.spray" %% "spray-testkit" % "1.3.1" % "test"
)
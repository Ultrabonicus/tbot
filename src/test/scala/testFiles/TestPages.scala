package testFiles

object TestPages {
	lazy val mainUnlogged = {
		val currentDir = System.getProperty("user.dir")
		val src = scala.io.Source.fromFile(currentDir + "/src/test/scala/testFiles/mainUnlogged.html")("UTF-8")
		val entity = src.mkString
		src.close
		entity
	}

	lazy val mainLogged = {
		val currentDir = System.getProperty("user.dir")
		val src = scala.io.Source.fromFile(currentDir + "/src/test/scala/testFiles/mainLogged.html")("UTF-8")
		val entity = src.mkString
		src.close
		entity
	}
	
	def getPage(name:String):String = {
		val currentDir = System.getProperty("user.dir")
		val src = scala.io.Source.fromFile(currentDir + "/src/test/scala/testFiles/" + name)("UTF-8")
		val entity = src.mkString
		src.close
		entity
	}
	
	lazy val blogPage = {
		getPage("BlogPage")
	}
	
	lazy val blogPage2 = {
		getPage("BlogPage2")
	}
	
}
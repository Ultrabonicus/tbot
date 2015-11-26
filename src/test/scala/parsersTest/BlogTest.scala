package parsersTest

// import org.specs2._
import org.specs2.specification._
import org.specs2.mutable._
import net.tbot.utils.PagesParser
import testFiles.TestPages

class BlogTest extends Specification with AllExpectations { 
	/*
	 * blogId:Long, blog:Long, name:String, 
	 * creator:String, readers:Long, rating:Long, 
	 * closed:Boolean, descriprion:Elem, 
	 * admins:List[Int], moderators:List[Int], 
	 * voteCount:Long, postsCount:Long, 
	 * created:Long
	 */
	sequential ^ "In package net.tbot.utils.PagesParser" ^  {
			"parse blog page" in {
				val res = PagesParser.parseBlog(TestPages.blogPage)
				println(res)
				true
			} 
			"parse second blog page" in {
				val res = PagesParser.parseBlog(TestPages.blogPage2)
				println(res)
				true
			}
	}
	
	lazy val testPage = testFiles.TestPages.getPage("blogs.html")
	
	
	
}
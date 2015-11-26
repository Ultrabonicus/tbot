package net.tbot.utils

import scala.xml.Elem
import scala.xml.NodeSeq
import scala.util.Try
import scalaz._
import syntax.apply._, syntax.validation._
import net.tbot.utils.Validate.validate

case class CommentFromStream(blog:String, blogName:String, topicTitle:String, author:String, 
															commentId:String, commentsCount:String)

object PagesParser {
	
	private val parser = scala.xml.XML.withSAXParser(new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl().newSAXParser())
	
	def parseCommentStream(text:String):Seq[CommentFromStream] = {
		val xml = parser.loadString(text)
		def parseComment(elem:NodeSeq):CommentFromStream = {
			val authorAndBlog = elem.\("p").\("a")
			val aCommentId = elem.\("a")
			CommentFromStream(				
				blog = authorAndBlog.tail.head.attribute("href").get.text,
				blogName = authorAndBlog.tail.head.text,
				commentId = aCommentId.head.attribute("href").get.text,
				topicTitle = aCommentId.head.text,
				author = authorAndBlog.head.text,
				commentsCount = elem.\("span").head.text
				)
		}
		val res:Seq[CommentFromStream] = xml.\\("ul").\\("li").map(parseComment(_)) 
		res
	}
	
	def parseBlog(unparsedHtml:String): ValidationNel[(Exception,Option[String]),Blog] = {
		val xml = parser.loadString(unparsedHtml)
		def main = xml.\\("div").filter(x => x.\@("id") == "content").head
		def topNode = main.child.filter(x => x.\@("class") == "blog-top").head
		def blogNode = main.child.filter(x => x.\@("id") == "blog").head
		def innerNode = blogNode.child.filter(x => x.\@("class") == "blog-inner").head
		def footerNode = blogNode.child.filter(x => x.\@("class") == "blog-footer").head
		
		val name = validate(topNode.\("h2").text.trim, Some("at Name"))
		val closed = validate[Boolean](topNode.\("h2").\("i").headOption.fold(false)(x => true), Some("at Closed"))
		val description = validate(innerNode.\("div").\("div").head,Some("at Description"))
		
		def voteItem = topNode.\("div").head
		val voteCount = validate(voteItem.\("div").\@("title").split(' ').last.toLong, Some("at VoteCount"))
		val blogId = validate(voteItem.\@("id").split("_").last.toLong, Some("at BlogId"))
		def voteTotalUnparsed = voteItem.\("div").\("span").text
		val voteTotal = validate(if(voteTotalUnparsed.head == '+') voteTotalUnparsed.tail.toDouble else voteTotalUnparsed.toDouble, Some("at VoteTotal"))
		
		val avatar = validate(innerNode.\("header").\("img").\@("src"), Some("at Avatar"))
		def innerContent = innerNode.\("div").head
		def innerContentInfo = innerContent.\("ul").head
		def innerContentDescription = innerContent.\("div").head
		val created = validate(innerContentInfo.\("li").head.\("strong").head.text, Some("at Created date")) //date
		val postCount = validate(innerContentInfo.\("li").tail.head.\("strong").head.text.toLong, Some("at postCount"))
		val readers = validate(innerContentInfo.\("li").tail.tail.head.\("strong").head.text.toLong, Some("at Reader"))
		def adminsSlice = innerContent.takeWhile(x => x.text != "Модераторы")
		def modsSlice = innerContent.reverse.takeWhile(x => x.text != "Модераторы")
		
		val admins = validate[Seq[String]](adminsSlice.filter(x => x.\@("class") == "user-avatar").map(x=>x.last.text), Some("at Admins list"))
		val moderators = validate[Seq[String]](modsSlice.filter(x => x.\@("class") == "user-avatar").map(x=>x.last.text), Some("at Mods list"))
		val creator = validate[String](footerNode.\("div").\("a").tail.head.text, Some("at Creator"))
		
		(blogId|@|name|@|creator|@|readers|@|voteTotal|@|closed|@|description|@|admins|@|moderators|@|voteCount|@|postCount|@|created) {
			Blog(_,_,_,_,_,_,_,_,_,_,_,_)
		}
	}
	
	def parseCommentsFromPost = ???
}

case class Blog(blogId:Long, name:String, creator:String, readers:Long, votesTotal:Double, closed:Boolean, descriprion:xml.Node, admins:Seq[String], moderators:Seq[String], voteCount:Long, postsCount:Long, created:String)

case class Comment(date:Long, blog:Option[String], postId:Option[Long], commentId:Long, author:Option[Long], body:Elem, parentId:Long, postTitle:Option[String], deleted:Boolean, favourite: Long, favourited: Boolean)

case class Poll(total:Int, notVoted:Int, Votes:List[Stand])

case class Stand(description:String, Percentage:Float, VotesQuanity:Int )

case class BlogPost(date:Long, blog:Option[String], postId:Long, author:String, title:String, draft:Boolean, voteCount:Long, voteTotal:Long, body:Elem, tags:Elem, commentsCount:Int, commentsNewCount:Int, short: Boolean, privat: Boolean, blogName: Option[String], poll:Poll)

case class PersonalTalk(talkId:Long, users:List[String], unread:Boolean, title:String, date:Long, body:Elem, author:Option[String], comments:List[Comment])

case class LoggedUserInfo(username:String, unreadTalkMessages:Long)
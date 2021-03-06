package net.tbot.utils

import spray.httpx.unmarshalling._
import java.nio.ByteBuffer
import java.io.{ InputStreamReader, ByteArrayInputStream }
import scala.xml.{ XML, NodeSeq }
import spray.http._
import MediaTypes._
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

trait CustomUnmarshaller {
	implicit val NodeSeqUnmarshaller =
		Unmarshaller[NodeSeq](`text/xml`, `application/xml`, `text/html`, `application/xhtml+xml`) {
			case HttpEntity.NonEmpty(contentType, data) =>
				val parser = (new SAXFactoryImpl).newSAXParser
				try {
					parser.setProperty("http://apache.org/xml/properties/locale", java.util.Locale.ROOT)
				} catch {
					case e: org.xml.sax.SAXNotRecognizedException ⇒ // property is not needed
				}
				XML.withSAXParser(parser).load(new InputStreamReader(new ByteArrayInputStream(data.toByteArray), contentType.charset.nioCharset))
			case HttpEntity.Empty => NodeSeq.Empty
		}
}
object CustomUnmarshaller extends CustomUnmarshaller
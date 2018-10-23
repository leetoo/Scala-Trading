package uk.co.tayloreddevelopment.trading.scala.rss

import scala.xml._
import java.net.URL

import uk.co.tradingdevelopment.trading.core.rss.PollingRxStreamer
import com.sun.syndication.feed.synd._
import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}
import java.io._

import org.htmlcleaner.HtmlCleaner

import scala.collection.immutable

case class RssItem(key:String,description:String, link:String)


object RssStreamerRome{
  lazy val cleaner = new HtmlCleaner
  val props = cleaner.getProperties
  props.setOmitComments(true)

  props.setPruneTags("script,style,img")

  props.setAllowHtmlInsideAttributes(false)

  def getRss(url:String):Vector[RssItem] =  getItems(loadUrl(url))


private def resolveNode(nodeSeq:NodeSeq) = nodeSeq.toVector.map(n =>   RssItem("RSS" ,(n \ "title").text + " " + (n \ "description").text,(n \ "link").text ))
private def getItems(tryFeed:Try[SyndFeed]): Vector[RssItem] = {

  tryFeed match {
    case Success(feed) => {
      val sf: SyndFeed = feed
      val javaItems = sf.getEntries
      sf
        .getEntries
        .asScala
        .toVector
        .map(e => {
          val i: SyndEntry = e.asInstanceOf[SyndEntry]
          val desc = i.getTitle + " :: " + i.getDescription.getValue
          val cleanTag = cleaner.clean(desc)

          RssItem("RSS", cleanTag.getText.toString, i.getLink)
        })


    }
    case Failure(ex) => {

      println(ex)
      Vector.empty[RssItem]
    }
  }

}


  private def loadUrl(url:String):Try[SyndFeed] = Try {
    val feedUrl = new URL(url)
    val input = new SyndFeedInput
    input.build(new XmlReader(feedUrl))


  }
}

class RssStreamerRome(interval:Int, rssFeeds:Vector[String]) extends PollingRxStreamer[RssItem](interval,Some(r => r.description)){
  override protected def getData: Vector[RssItem] = for {
    feed <- rssFeeds
    item <- RssStreamerRome.getRss(feed)
  } yield item

}


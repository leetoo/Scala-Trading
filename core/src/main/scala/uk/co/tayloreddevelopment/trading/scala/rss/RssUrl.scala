package uk.co.tradingdevelopment.trading.scala.rss
import java.net.URL
case class RssUrl(url:URL) {
  override def toString = "RSS: " + url.toString
}
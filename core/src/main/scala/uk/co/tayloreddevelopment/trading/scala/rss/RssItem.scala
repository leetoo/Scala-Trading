package uk.co.tradingdevelopment.trading.scala.rss

import java.util.Date

case class RssItem(title:String, link:String, desc:String, date:Date, guid:String) {
  override def toString = date + " " + title
}
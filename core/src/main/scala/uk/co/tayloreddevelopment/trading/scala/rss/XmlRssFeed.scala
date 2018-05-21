package uk.co.tradingdevelopment.trading.scala.rss

case class XmlRssFeed(title:String, link:String, desc:String, language:String, items:Seq[RssItem]) extends RssFeed
package uk.co.tradingdevelopment.trading.scala.rss

case class AtomRssFeed(title:String, link:String, desc:String, items:Seq[RssItem]) extends RssFeed
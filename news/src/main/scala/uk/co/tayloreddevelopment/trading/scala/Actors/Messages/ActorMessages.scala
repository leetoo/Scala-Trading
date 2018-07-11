package uk.co.tayloreddevelopment.trading.scala.news.actors.messages

import uk.co.tradingdevelopment.trading.scala.sentiment.Sentiment
case class SubscribeToTwitter(start: Boolean)
case class SubscribeToKafta(start:Boolean)
case class SubscribeToRss(feeds:Vector[String])

case class Filter(key:String,source:String, text:String)
case class ProcessLinks(key:String,filter:String,source:String, text:String)
case class GetSentiment(key:String,filter:String,source:String, text:String, links:Vector[String])
case class NotifySentiment(key:String,filter:String,source:String, text:String, links:Vector[String], sentiment:Sentiment)
//case class ProcessLink(source:String,text:String,tag:String)
//case class NotifySentiment(tag:String, text:String, source:String, sentiment:Sentiment, link:Vector[String])
//
//case class GetSentiment(tag:String,text:String, link:List[String], source:String)
//
//case class MessageToFilter(text:String, link:String, source:String)
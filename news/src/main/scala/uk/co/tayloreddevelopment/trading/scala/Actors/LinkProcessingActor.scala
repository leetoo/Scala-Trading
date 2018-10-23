package uk.co.tayloreddevelopment.trading.scala.news.actors

import akka.actor.{Actor, ActorRef, Props}
import akka.stream.ActorMaterializer
import com.fasterxml.jackson.databind.JsonNode
import com.typesafe.config.{Config, ConfigFactory}
import play.api.libs.ws.ahc.AhcWSClient
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tayloreddevelopment.trading.scala.twitter.TwitterItem
import uk.co.tradingdevelopment.trading.core.rss.RssItem
import uk.co.tradingdevelopment.trading.core.sentiment.CoreNLPSentiment
import uk.co.tradingdevelopment.trading.core.web.LinkProcessor
import  uk.co.tayloreddevelopment.trading.scala.news.actors.messages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LinkProcessingActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()
  lazy val sentiment = new CoreNLPSentiment()
  implicit val materializer = ActorMaterializer()
  lazy val wsClient = AhcWSClient()
  lazy val linkProcessor = new LinkProcessor(wsClient)
  val filterActor: ActorRef = this.context.actorOf(Props[TagFilterActor])
  val sentimentActor = this.context.actorOf(Props[SentimentActor])
  override def receive: Receive = {
case x:ProcessLinks => {
  linkProcessor.process(x.text) match {
    case Some(t) => sentimentActor ! GetSentiment(x.key,x.filter,x.source,t._2,t._1)
    case None => ()
  }

}
    case _ => println("I don't know how to process this message")
  }
}

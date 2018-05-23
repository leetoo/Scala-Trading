package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.{Actor, ActorRef, Props}
import akka.stream.ActorMaterializer
import com.fasterxml.jackson.databind.JsonNode
import com.typesafe.config.{Config, ConfigFactory}
import play.api.libs.ws.ahc.AhcWSClient
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tayloreddevelopment.trading.scala.twitter.TwitterItem
import uk.co.tradingdevelopment.trading.scala.rss.RssItem
import uk.co.tradingdevelopment.trading.scala.sentiment.CoreNLPSentiment
import uk.co.tradingdevelopment.trading.scala.web.LinkProcessor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LinkProcessingActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()
  lazy val sentiment = new CoreNLPSentiment()
  implicit val materializer = ActorMaterializer()
  lazy val wsClient = AhcWSClient()
  lazy val linkProcessor = new LinkProcessor(wsClient)
  val filterActor: ActorRef = this.context.actorOf(Props[TagFilterActor])
  override def receive: Receive = {
    case x: RssItem =>
     linkProcessor.process(x.description + " " + x.link) match {
       case Some(t) => filterActor ! MessageToFilter(t,x.link,"<RSS>")
       case None => ()
     }
    case x:TwitterItem => filterActor ! MessageToFilter(x.user,x.text, "<TWITTER>")

    case _ => println("I don't know how to process this message")
  }
}

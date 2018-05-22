package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tradingdevelopment.trading.scala.rss.RssStreamer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class SubscribeToRss(feeds:Vector[String])
class RssReaderActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()

  val linkProcessingActor: ActorRef = this.context.actorOf(Props[LinkProcessingActor])
  override def receive: Receive = {
    case x:SubscribeToRss => val rssStream = new RssStreamer(60,x.feeds)
      rssStream.start.subscribe(i =>{
        linkProcessingActor ! i

      } )
    case _ => println("I don't know how to process this message")
  }
}

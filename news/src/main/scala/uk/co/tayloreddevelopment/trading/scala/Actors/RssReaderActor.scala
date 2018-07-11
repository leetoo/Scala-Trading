package uk.co.tayloreddevelopment.trading.scala.news.actors

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tradingdevelopment.trading.scala.rss.RssStreamer
import  uk.co.tayloreddevelopment.trading.scala.news.actors.messages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class RssReaderActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()

  val filterProcessingActor: ActorRef = this.context.actorOf(Props[TagFilterActor])
  override def receive: Receive = {
    case x:SubscribeToRss => val rssStream = new RssStreamer(60,x.feeds)
      rssStream.start.subscribe(i =>{
        filterProcessingActor ! Filter(i.key,"<RSS>", Vector(i.description,i.link).mkString(" "))

      } )
    case _ => println("I don't know how to process this message")
  }
}

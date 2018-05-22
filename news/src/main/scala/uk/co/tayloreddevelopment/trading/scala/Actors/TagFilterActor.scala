package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
case class MessageToFilter(text:String, link:String)
class TagFilterActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()

 lazy val tags = Vector("eur","ftse")
  val sentimentActor: ActorRef = this.context.actorOf(Props[SentimentActor])
  override def receive: Receive = {
    case x: MessageToFilter =>tags.foreach(tg => x.text.contains(tg) match {
      case true =>  sentimentActor ! GetSentiment(tg,x.text,x.link)
      case false => ()
    })
    case _ => println("I don't know how to process this message")
  }
}

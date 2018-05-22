package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tradingdevelopment.trading.scala.sentiment.CoreNLPSentiment

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//case class GetSentiment(tag:String,text:String)
case class GetSentiment(tag:String,text:String, link:String)
class SentimentActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()
lazy val sentiment = new CoreNLPSentiment()
val notificationActor: ActorRef = this.context.actorOf(Props[NotificationActor])
  override def receive: Receive = {
    case x: GetSentiment =>
      notificationActor ! NotifySentiment(x.tag,x.text,sentiment.getSentiment(x.text),x.link)
    case _ => println("I don't know how to process this message")
  }
}

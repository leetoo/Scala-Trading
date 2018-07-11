package uk.co.tayloreddevelopment.trading.scala.news.actors

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.config.{Config, ConfigFactory}
import edu.stanford.nlp.pipeline.CoreNLPProtos
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tradingdevelopment.trading.scala.sentiment.{CoreNLPSentiment, NaiveSentiment, SseSentiment}
import uk.co.tayloreddevelopment.trading.scala.news.actors.messages._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//case class GetSentiment(tag:String,text:String)

class SentimentActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()
//lazy val sentiment = new NaiveSentiment()
  lazy val sentiment  =new  SseSentiment
val notificationActor: ActorRef = this.context.actorOf(Props[NotificationActor])
  override def receive: Receive = {
    case x: GetSentiment =>
      notificationActor ! NotifySentiment(x.key,x.filter,x.source,x.text,x.links,sentiment.getSentiment(x.text))
    case _ => println("I don't know how to process this message")
  }
}

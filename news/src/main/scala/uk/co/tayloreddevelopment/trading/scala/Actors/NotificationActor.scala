package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.Actor
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tradingdevelopment.trading.scala.notifier.{High, PushoverNotifier}
import uk.co.tradingdevelopment.trading.scala.sentiment.{CoreNLPSentiment, Sentiment}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
case class NotifySentiment(tag:String, text:String, source:String, sentiment:Sentiment, link:String)
class NotificationActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()
  lazy val client = new PushoverNotifier("ar246pddo2bhgn4c6axx4648dm4tue","gnpdagjyiam46hge3b6a4srt535jk2")

  override def receive: Receive = {
    case x: NotifySentiment =>
      client.Notify(s"${x.tag} was ${x.sentiment.toString} :${x.source} ",x.text, x.link,High)

    case _ => println("I don't know how to process this message")
  }
}

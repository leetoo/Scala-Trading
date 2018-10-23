package uk.co.tayloreddevelopment.trading.scala.news.actors

import akka.actor.Actor
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tradingdevelopment.trading.core.notifier.{High, PushoverNotifier}
import uk.co.tradingdevelopment.trading.core.sentiment.{CoreNLPSentiment, Sentiment}
import  uk.co.tayloreddevelopment.trading.scala.news.actors.messages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class NotificationActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()
  lazy val client = new PushoverNotifier("ar246pddo2bhgn4c6axx4648dm4tue","gnpdagjyiam46hge3b6a4srt535jk2")

  override def receive: Receive = {
    case x: NotifySentiment =>
      client.Notify(s"${x.filter} was ${x.sentiment.toString} :${x.key} via ${x.source}",x.text, x.links,High)

    case _ => println("I don't know how to process this message")
  }
}

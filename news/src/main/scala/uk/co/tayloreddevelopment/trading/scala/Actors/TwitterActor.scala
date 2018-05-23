package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.{Actor, ActorRef, Props}
import com.danielasfregola.twitter4s.entities.Tweet
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tayloreddevelopment.trading.scala.twitter.{
  TwitterItem,
  TwitterStreamer
}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success
case class SubscribeToTwitter(start: Boolean)

class TwitterActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()

  val filters = Vector(
    "LSEplc",
    "SHARESmag‏",
    "WarrenBuffett",
    "alphatrends‏",
    "FousAlerts‏",
    "DanZanger‏",
    "traderstewie‏",
    "harmongreg‏",
    "WSJmarkets‏",
    "The_Real_Fly‏",
    "EIAgov",
    "Schuldensuehner",
    "paulkrugman",
    "AswathDamodaran",
    "ukarlewitz",
    "lindayueh",
    "JStanleyFX",
    "JMahony_IG",
    "IGTV",
    "IGClientHelp",
    "IGSquawk‏",
    "IGcom",
    "OANDAbusiness",
    "OANDAlerts",
    "OANDA",
    "markets",
    "FTAlphaville",
    "FT",
    "coindesk",
    "Reuters",
    "SkyNewsBiz",
    "BBCBusiness",
    "ChrisB_IG",
    "LiveSquawk",
    "@DailyFXTeam",
    "@CNBC",
    "@economics",
    "@RANsquawk",
    "@business",
    "@CityAM",
    "@ReutersBiz",
    "@ForexLive",
    "@nikolaslippmann"
  )

  val linkProcessingActor: ActorRef =
    this.context.actorOf(Props[LinkProcessingActor])
  override def receive: Receive = {
    case x: SubscribeToTwitter =>
      val twit = new TwitterStreamer(
        "6DziMXM3V4kBy4Wfb3UOPGUCa",
        "fdG9iSewXj1GgDeGRtkJLIctn2mobSLYF6zcDT5x2bth5d6TCx",
        "1068725924-WBigfGfCp5YVXpGENho6WVbKyFyr8cJUnDjf4s2",
        "isM3ualk8ryQ12TpkpbFg1xsr9ZmUTBNiOTKUltiPly4L", {
          case tweet: Tweet =>
            tweet.user match {
              case Some(u) =>
                filters.contains(u.screen_name) match {
                  case true =>
                    val item = TwitterItem(tweet.user match {
                      case Some(u) => u.screen_name
                      case None    => "unknown"
                    }, tweet.text)
                    linkProcessingActor ! item
                    println(item)
                  case false => ()
                }
              case None => ()
            }

        }
      )
    case _ => println("I don't know how to process this message")
  }
}

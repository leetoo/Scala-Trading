package uk.co.tayloreddevelopment.trading.scala.news.actors

import akka.actor.{Actor, ActorRef, Props}
import com.danielasfregola.twitter4s.entities.Tweet
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tayloreddevelopment.trading.scala.twitter.{TwitterItem, TwitterStreamer}
import  uk.co.tayloreddevelopment.trading.scala.news.actors.messages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Success, Try}


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

  val filterProcessingActor: ActorRef =
    this.context.actorOf(Props[TagFilterActor])
  override def receive: Receive = {
    case x: SubscribeToTwitter =>
      Try{val twit = new TwitterStreamer(
        "6DziMXM3V4kBy4Wfb3UOPGUCa",
        "fdG9iSewXj1GgDeGRtkJLIctn2mobSLYF6zcDT5x2bth5d6TCx",
        "1068725924-WBigfGfCp5YVXpGENho6WVbKyFyr8cJUnDjf4s2",
        "isM3ualk8ryQ12TpkpbFg1xsr9ZmUTBNiOTKUltiPly4L", tweet => {

            tweet.user match {
              case Some(u) =>

//                filters.contains(u.screen_name) match {
//                  case true =>
                    print(tweet.text)

                    filterProcessingActor !  Filter(tweet.user match {
                      case Some(u) => u.screen_name
                      case None    => "unknown"
                    },"<TWITTER>",tweet.text)

//
//                  case false => ()
//                }
              case None => ()
            }

        }
      )}.recover {case ex:Exception => {
        println(ex.getMessage)
      }}
    case _ => println("I don't know how to process this message")
  }
}

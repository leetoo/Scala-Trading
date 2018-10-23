package uk.co.tayloreddevelopment.trading.scala.news

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import uk.co.tayloreddevelopment.trading.scala.news.actors._
import uk.co.tayloreddevelopment.trading.scala.news.actors.messages._
import uk.co.tradingdevelopment.trading.core.logging._

import scala.concurrent._
import scala.concurrent.duration._
import scala.io.StdIn._
import scala.util.Try




object News extends App {

  val system: ActorSystem = ActorSystem("news")
  private lazy val conf: Config = ConfigFactory.load()
  private lazy val mongoHost = conf.getString("mongo.host")
  private lazy val mongoPort = conf.getString("mongo.port")

  println("Actor system created")
  println(system)
  val tweetActor = system.actorOf(Props[TwitterActor])
  val rssActor = system.actorOf(Props[RssReaderActor])


  print(Perf.GetPerformance)


  tweetActor ! SubscribeToTwitter(true)
  rssActor ! SubscribeToRss(
    Vector("http://lse.einnews.com/rss/bia3tK9kGDSwNBad",
      "http://www.prnewswire.co.uk/rss/all-news-releases-from-PR-newswire-news.rss",
      "http://www.prnewswire.co.uk/rss/news-for-investors-from-PR-Newswire-news.rss",
      "http://www.prnewswire.co.uk/rss/financial-services/earnings-forecasts-projections-news.rss",
      "http://www.prnewswire.co.uk/rss/financial-services/all-financial-services-news.rss",
    //  "https://www.investegate.co.uk/Rss.aspx?type=2",
     // "https://www.investegate.co.uk/Rss.aspx?type=32",
     // "https://www.investegate.co.uk/Rss.aspx?type=4",
     // "https://www.investegate.co.uk/Rss.aspx?type=1",
      "http://feeds.bbci.co.uk/news/business/companies/rss.xml"))

  println("Press any key to quit")
  val input = readLine
  tweetActor ! SubscribeToKafta(false)

  Await.result(system.terminate(), 60 seconds)
  System.exit(0)
}


package uk.co.tayloreddevelopment.trading.scala

import akka.actor.{ActorSystem, Props}
import uk.co.tayloreddevelopment.trading.scala.Actors._

import scala.concurrent._
import scala.concurrent.duration._
import scala.io.StdIn._


object News extends App {

  val system: ActorSystem = ActorSystem("news")


  println("Actor system created")
  println(system)
  val tweetActor = system.actorOf(Props[TwitterActor])
  val rssActor = system.actorOf(Props[RssReaderActor])


  tweetActor ! SubscribeToTwitter(true)
  rssActor ! SubscribeToRss(Vector("http://lse.einnews.com/rss/bia3tK9kGDSwNBad"))

  println("Press any key to quit")
  val input = readLine
  tweetActor ! SubscribeToKafta(false)

  Await.result(system.terminate(), 60 seconds)
  System.exit(0)
}

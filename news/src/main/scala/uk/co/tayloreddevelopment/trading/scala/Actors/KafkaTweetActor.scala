package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tradingdevelopment.trading.scala.kafka.{KafkaItem, KafkaServers, KafkaSubscriber}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
case class SubscribeToKafta(start:Boolean)
class KafkaTweetActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()

  val linkProcessingActor: ActorRef = this.context.actorOf(Props[LinkProcessingActor])

val sub =  new KafkaSubscriber(KafkaServers("localhost:9092","localhost:2181"),
  "rawtweets",60, r => {
    println("TWEET RECEIVED " + r.key())

    linkProcessingActor ! KafkaItem(r.key(),r.value())

  })

  override def receive: Receive = {
    case x: SubscribeToKafta => x.start match {
      case true => println("Subscribing")
        sub.run()
      case false => println("Unsubscribing")
        sub.stop()
    }
    case _ => println("I don't know how to process this message")
  }
}

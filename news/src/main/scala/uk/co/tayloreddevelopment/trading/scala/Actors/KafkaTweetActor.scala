package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.Actor
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
case class SubscribeToKafta(start:Boolean)
class KafkaTweetActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()


  override def receive: Receive = {
    case x: SubscribeToKafta => x.start match {
      case true => println("Subscribing")
      case false => println("Unsubscribing")
    }
    case _ => println("I don't know how to process this message")
  }
}

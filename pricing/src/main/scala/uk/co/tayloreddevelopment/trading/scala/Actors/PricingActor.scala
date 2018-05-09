package uk.co.tayloreddevelopment.trading.scala.Actors

import akka.actor.Actor
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PricingActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()
  val driver = new reactivemongo.api.MongoDriver
  val dbConnection: MongoConnection = driver.connection(List(conf.getString("mongo.host")))
  val db: Future[DefaultDB] = dbConnection.database(conf.getString("mongo.db"))

  override def receive: Receive = {

    case _ => println("I don't know how to process this message")
  }
}

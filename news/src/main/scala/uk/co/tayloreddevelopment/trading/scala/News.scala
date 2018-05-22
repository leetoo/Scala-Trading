package uk.co.tayloreddevelopment.trading.scala

import akka.actor.{ActorSystem, Props}


import scala.concurrent._
import scala.concurrent.duration._
import scala.io.StdIn._


object News extends App {

  val system: ActorSystem = ActorSystem("news")


  println("Actor system created")
  println(system)
  //val pricingActor = system.actorOf(Props[PricingActor])



  println("Press any key to quit")
  val input = readLine
  Await.result(system.terminate(), 60 seconds)
  System.exit(0)
}

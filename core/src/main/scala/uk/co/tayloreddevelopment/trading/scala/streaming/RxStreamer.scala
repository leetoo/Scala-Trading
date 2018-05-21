package uk.co.tradingdevelopment.trading.scala.rss

import monix.reactive.subjects._
import monix.reactive._

trait RxStreamer[A]{
 def start:AsyncSubject[A]
def stop
}
package uk.co.tradingdevelopment.trading.scala.rss

import java.time.LocalDateTime

import monix.reactive.{Observable, Observer}
import monix.reactive.subjects.AsyncSubject
import uk.co.tradingdevelopment.trading.scala.collections.HashValidator

import scala.collection.immutable.Vector
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
abstract class PollingRxStreamer[A](interval: Int) extends RxStreamer[A] {
  protected def getData: Vector[A]
  private lazy val validator = new HashValidator[A](interval,100)
  lazy val stream:AsyncSubject[A] =AsyncSubject[A]
  var complete = false
  private def collectDataForStream: Future[Boolean] = Future {
    while (!complete) {
      getData.foreach(d => {
        validator.validate(d) match {
          case None    => ()
          case Some(v) => stream.onNext(v)
        }

      })
      Thread.sleep(interval * 1000)

    }
    true
  }

  def start: AsyncSubject[A] = {
    collectDataForStream
    stream
  }
  def stop = {
    complete = true

  }
}

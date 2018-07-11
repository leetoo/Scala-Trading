package uk.co.tradingdevelopment.trading.scala.rss

import java.time.LocalDateTime


import rx.lang.scala._
import rx.lang.scala.observables._
import rx.lang.scala.schedulers._
import uk.co.tradingdevelopment.trading.scala.collections.HashValidator
import scala.collection.immutable.Vector
import scala.concurrent.Future
//import scala.concurrent.ExecutionContext.Implicits.global
abstract class PollingRxStreamer[A](interval: Int, hasher:Option[A => String]) extends RxStreamer[A] {
  protected def getData: Vector[A]
  private lazy val validator = new HashValidator[A](interval,100,hasher)
  lazy val stream:Observable[A] = {
    Observable(
      subscriber => {
        // For simplicity this example uses a Thread instead of an ExecutorService/ThreadPool
        new Thread(new Runnable() {
          def run() {
            while (!complete) {
              if (subscriber.isUnsubscribed) {
                return
              }

              getData.foreach(d => {
                validator.validate(d) match {
                  case None    => ()
                  case Some(v) => subscriber.onNext(v)
                }

              })
              Thread.sleep(interval * 1000)

            }

          }
        }).start()

      }
    )
  }
  var complete = false
  def start: Observable[A] = stream
  def stop = {
    complete = true

  }
}

package uk.co.tradingdevelopment.trading.scala.collections
import uk.co.tradingdevelopment.trading.scala.timing._
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._
import org.scalatest._
import monix.execution.Scheduler.{global => scheduler}
import Matchers._
import monix.reactive.Consumer
import monix.reactive.observers.Subscriber
import uk.co.tradingdevelopment.trading.scala.rss.PollingRxStreamer
class PollingRxStreamerSpec extends FunSpec {

  case class TestStreamer() extends PollingRxStreamer[Int](1) {
    override def getData: Vector[Int] = Vector(1,2,3,4,5)
  }
//https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A Polling Rx Streamer") {

    it("should stream filtered values") {
//     val streamer  = new TestStreamer()
//
//      val o = streamer.start
//      o.consumeWith(Consumer.complete).runAsync
//
//      Thread.sleep(5000)

    }
 }
}

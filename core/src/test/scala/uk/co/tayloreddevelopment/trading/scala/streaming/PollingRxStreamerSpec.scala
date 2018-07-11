package uk.co.tradingdevelopment.trading.scala.streaming
import uk.co.tradingdevelopment.trading.scala.timing._
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._
import org.scalatest._

import Matchers._

import uk.co.tradingdevelopment.trading.scala.rss.PollingRxStreamer

class PollingRxStreamerSpec extends FunSpec {

  case class TestStreamer() extends PollingRxStreamer[Int](1,None) {
    override def getData: Vector[Int] = Vector(1, 2, 3, 4, 5)
  }
//https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A Polling Rx Streamer") {

    it("should stream filtered values") {
      val streamer = new TestStreamer()
      var list = List[Int]()
      val o = streamer.start
      o.subscribe(s => {
        println(s)
        list = s :: list
      })
      Thread.sleep(5000)
      streamer.stop
      list.reverse shouldBe List(1, 2, 3, 4, 5)
    }
  }
}

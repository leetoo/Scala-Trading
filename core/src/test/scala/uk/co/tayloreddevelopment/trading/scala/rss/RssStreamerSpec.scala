package uk.co.tradingdevelopment.trading.scala.rss
import uk.co.tradingdevelopment.trading.scala.timing._
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._
import org.scalatest._

import Matchers._

import uk.co.tradingdevelopment.trading.scala.rss.PollingRxStreamer

class RssStreamerSpec extends FunSpec {


//https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A RssStreamer") {

    it("should stream values") {
      val streamer = new RssStreamer(1,Vector("http://lse.einnews.com/rss/bia3tK9kGDSwNBad"))


      var list = List[RssItem]()
      val o = streamer.start
      o.subscribe(s => {
        println(s.description)
        list = s :: list
      })
      Thread.sleep(5000)
    streamer.stop
    }
  }
}

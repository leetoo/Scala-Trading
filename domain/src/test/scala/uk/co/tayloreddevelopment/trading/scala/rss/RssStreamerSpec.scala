package uk.co.tradingdevelopment.trading.core.rss
import uk.co.tradingdevelopment.trading.core.timing._
import uk.co.tradingdevelopment.trading.core.operators.Pipe._
import org.scalatest._

import Matchers._

import uk.co.tradingdevelopment.trading.core.rss.PollingRxStreamer

class RssStreamerSpec extends FunSpec {


//https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A RssStreamer") {

    it("should stream values") {
      val streamer = new RssStreamer(10,Vector(   "https://www.investegate.co.uk/Rss.aspx?type=2",
      "https://www.investegate.co.uk/Rss.aspx?type=32",
      "https://www.investegate.co.uk/Rss.aspx?type=4",
      "https://www.investegate.co.uk/Rss.aspx?type=1"))


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

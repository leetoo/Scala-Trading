package uk.co.tayloreddevelopment.trading.scala.rss

import org.scalatest._

class RssStreamerRomeSpec extends FunSpec {


//https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A RssStreamer") {

    it("should stream values") {
      val streamer = new RssStreamerRome(10,Vector(   "https://www.investegate.co.uk/Rss.aspx?type=2",
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

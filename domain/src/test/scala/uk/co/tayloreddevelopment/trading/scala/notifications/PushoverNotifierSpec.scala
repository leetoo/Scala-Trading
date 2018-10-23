package uk.co.tradingdevelopment.trading.core.notifications
import uk.co.tradingdevelopment.trading.core.timing._
import uk.co.tradingdevelopment.trading.core.operators.Pipe._
import org.scalatest._
import Matchers._
import uk.co.tradingdevelopment.trading.core.notifier.{High, PushoverNotifier}
import uk.co.tradingdevelopment.trading.core.rss.PollingRxStreamer

class PushoverNotifierSpec extends FunSpec {

  // API Token ar246pddo2bhgn4c6axx4648dm4tue
  // USER Token gnpdagjyiam46hge3b6a4srt535jk2
//https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A PushoverNotifierSpec") {

    it("should send messages to client") {
     val notifier = new PushoverNotifier("ar246pddo2bhgn4c6axx4648dm4tue","gnpdagjyiam46hge3b6a4srt535jk2")
      notifier.Notify("Unit Test","Quick Message",Vector("http\\:www.google.co.uk"),High)
    }
  }
}

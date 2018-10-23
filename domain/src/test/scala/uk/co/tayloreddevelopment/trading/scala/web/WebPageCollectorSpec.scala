package uk.co.tayloreddevelopment.trading.scala.web

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.Matchers._
import org.scalatest._
import play.api.libs.ws.ahc.AhcWSClient
import uk.co.tradingdevelopment.trading.core.rss.PollingRxStreamer
import uk.co.tradingdevelopment.trading.core.web.{LinkProcessor, WebPageCollector}

class WebPageCollectorSpec extends FunSpec {

  //https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A Web Page Collector") {

    it("should collect links from text text from page") {
      val link =  "https://t.co/jU07OnP90t"
      implicit val system: ActorSystem = ActorSystem("test")
      implicit val materializer = ActorMaterializer()
      lazy val wsClient = AhcWSClient()
      lazy val collector = new WebPageCollector(wsClient)
      val webText = collector.collectWebPageText(link,removeScripts = true)
      println(webText)

    }
  }
}




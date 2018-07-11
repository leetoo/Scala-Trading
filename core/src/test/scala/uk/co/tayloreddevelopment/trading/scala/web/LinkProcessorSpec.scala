package uk.co.tradingdevelopment.trading.scala.web
import uk.co.tradingdevelopment.trading.scala.timing._
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._
import org.scalatest._
import Matchers._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.AhcWSClient
import uk.co.tradingdevelopment.trading.scala.rss.PollingRxStreamer
import uk.co.tradingdevelopment.trading.scala.web.LinkProcessor

class LinkProcessorSpec extends FunSpec {


  //https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A Link Processor") {

    it("should collect links from text") {
      val text =  "De La Rue reports profit drop in first results since passport controversy https://t.co/jU07OnP90t https://t.co/ULMffvbNqC"
      implicit val system: ActorSystem = ActorSystem("test")
      implicit val materializer = ActorMaterializer()
      lazy val wsClient = AhcWSClient()
      lazy val linkProcessor = new LinkProcessor(wsClient)
      val links: (Vector[String], String) = linkProcessor.process(text).get
     val list =  links._1
    list.foreach(println)

      list.size shouldBe(2)
    }
  }
}




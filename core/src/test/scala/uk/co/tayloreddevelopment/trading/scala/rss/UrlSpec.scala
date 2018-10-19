package uk.co.tayloreddevelopment.trading.scala.rss
import scala.xml._
import org.scalatest._
import java.net._

import scala.util.Try
class UrlSpec extends FunSpec {


//https://lse.einnews.com/rss/bia3tK9kGDSwNBad
  describe("A RssStreamer") {

    it("should stream values") {

      val url = new URL("https://www.investegate.co.uk/Rss.aspx?type=2")
val cont =Try( url.getContent)
      val test = XML.load(url)

    }
  }
}

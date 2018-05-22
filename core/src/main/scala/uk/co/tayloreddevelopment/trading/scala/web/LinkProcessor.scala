package uk.co.tradingdevelopment.trading.scala.web

import com.fasterxml.jackson.databind.JsonNode
import play.api.libs.ws.WSClient
import uk.co.tradingdevelopment.trading.scala.logging.Loggable
import uk.co.tradingdevelopment.trading.scala.defaults.JsonDefaults._

import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}


class LinkProcessor(ws: WSClient) extends Loggable {
  lazy val linkRegEx: Regex =
    "(http|https|ftp|ftps)\\:\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(\\/\\S*)?".r
  private lazy val pageCollector = new WebPageCollector(ws)
  def processs(id: String, obfuscate: Boolean, text: String): Option[JsonNode] =
    Try {
      text +: linkRegEx
        .findAllIn(text)
        .map(t => pageCollector.collectWebPageText(id, obfuscate, t, removeScripts = true))
        .toVector

    } match {
      case Success(txt) =>
        println(txt)
        val combined = txt.mkString(" ")
        Some(SimpleJsonNode("text", combined))
      case Failure(_) => None
    }

}

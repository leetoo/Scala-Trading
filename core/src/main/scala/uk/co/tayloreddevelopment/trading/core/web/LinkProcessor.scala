package uk.co.tradingdevelopment.trading.core.web

import com.fasterxml.jackson.databind.JsonNode
import play.api.libs.ws.WSClient
import uk.co.tradingdevelopment.trading.core.logging.Loggable
import uk.co.tradingdevelopment.trading.core.defaults.JsonDefaults._

import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}


class LinkProcessor(ws: WSClient) extends Loggable {
 private lazy val linkRegEx: Regex =
    "(http|https|ftp|ftps)\\:\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(\\/\\S*)?".r
  private lazy val pageCollector = new WebPageCollector(ws)
  def process(text: String): Option[(Vector[String],String)] =
    Try {
      val links = linkRegEx
        .findAllIn(text).toList

      val txt =  pageCollector.collectWebPageText(links.head, removeScripts = true)
  //    val txt = links.map(t => pageCollector.collectWebPageText(t, removeScripts = true)).toVector

      (links.map(p => p).toVector,text + " " +  txt.mkString(" "))

    } match {
      case Success(txt) =>
        Some( (txt._1,txt._2))
      case Failure(_) => None
    }

}

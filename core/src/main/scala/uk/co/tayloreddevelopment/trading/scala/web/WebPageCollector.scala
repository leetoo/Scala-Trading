package uk.co.tradingdevelopment.trading.scala.web

import java.net.URLDecoder
import java.util.concurrent.TimeUnit

import com.fasterxml.jackson.databind.node.ArrayNode
import com.typesafe.config.{Config, ConfigFactory}

import collection.JavaConverters._
import org.htmlcleaner.{DomSerializer, HtmlCleaner, TagNode}
import org.joda.time.DateTime
import play.api.libs.ws._
import uk.co.tradingdevelopment.trading.scala.logging.{CORE, Loggable}
import uk.co.tradingdevelopment.trading.scala.extensions.GenericCollectionExtensions._
import uk.co.tradingdevelopment.trading.scala.extensions.GenericExtensions._
import uk.co.tradingdevelopment.trading.scala.extensions.JsonExtensions._
import uk.co.tradingdevelopment.trading.scala.extensions.NativeTypeExtensions._
import uk.co.tradingdevelopment.trading.scala.defaults.JsonDefaults._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

class WebPageCollector(ws: WSClient) extends Loggable {

  def getSourceFromUrl(url: String): String =
    Await.result(ws.url(url).get(), Duration(2, TimeUnit.MINUTES)).body

  def collectWebPageTables(id: String,
                           obfuscate: Boolean,
                           docUrl: String,
                           removeScripts: Boolean,
                           xpath: String): ArrayNode = {
    Try {
      val cleaner = new HtmlCleaner
      val cleanerProperties = cleaner.getProperties

      cleanerProperties.setAdvancedXmlEscape(true)

      cleanerProperties.setOmitXmlDeclaration(true)
      cleanerProperties.setOmitDoctypeDeclaration(false)

      cleanerProperties.setTranslateSpecialEntities(true)
      cleanerProperties.setTransResCharsToNCR(true)
      cleanerProperties.setRecognizeUnicodeChars(true)

      cleanerProperties.setIgnoreQuestAndExclam(true)
      cleanerProperties.setUseEmptyElementTags(false)

      cleanerProperties.setPruneTags("script,title")

      val raw = getSourceFromUrl(docUrl)

      val table: TagNode = cleaner
        .clean(raw)
        .evaluateXPath(xpath)
        .map(_.asInstanceOf[TagNode])
        .head

      val headerRow: List[String] = table
        .evaluateXPath("//thead/tr/th")
        .map(_.asInstanceOf[TagNode].getText.toString)
        .toList
      val bodyRows: List[List[String]] = table
        .evaluateXPath("//tbody/tr")
        .map(r => r.asInstanceOf[TagNode])
        .toList
        .map(
          r =>
            r.evaluateXPath("//td")
              .map(
                _.asInstanceOf[TagNode].getText.toString
                  .replaceAll("'s", "''s")
                  .replaceAll("&nbsp;", " ")
                  .replaceAll("\"", "'")
                  .recursivelyReplaceAll("\n", " ")
                  .recursivelyReplaceAll("  ", " "))
              .toList)

      val an = TableArrayNode(headerRow, bodyRows)
      logInfo( CORE, Some(s"Table Array Node"), an)(id)
      an

    } match {
      case Success(r) => r
      case Failure(ex) => {
        logError(CORE,
          Some(s"Could not get lift html for doc  $docUrl"),
          ex)(id)
        SimpleJsonArrayNode("error", ex.getMessage)
      }
    }

  }

  def collectWebPageText(id: String,
                         obfuscate: Boolean,
                         docUrl: String,
                         removeScripts: Boolean): String = {
    Try {
      val cleaner = new HtmlCleaner
      val props = cleaner.getProperties

      if (removeScripts) {
        props.setOmitComments(true)
        props.setPruneTags("script,style,img")
        //props.setAllowTags("body,div,p")
        props.setAllowHtmlInsideAttributes(false)

      } else {
        props.setOmitComments(true)
      }
      val raw = getSourceFromUrl(docUrl)

      val cleanTag = cleaner.clean(raw)
      cleanTag.getText.toString
      val cleanedText = cleanTag.getText.toString

      val txt = cleanedText
        .replaceAll("'s", "''s")
        .replaceAll("&nbsp;", " ")
        .replaceAll("\"", "'")
        .recursivelyReplaceAll("\n", " ")
        .recursivelyReplaceAll("  ", " ")

      logInfo(CORE, Some(s"Lifted Text"), txt)(id)
      txt

    } match {
      case Success(r) =>
        Try(URLDecoder.decode(r, "UTF-8")) match {
          case Success(d) => {
            d
          }
          case Failure(dex) => {
            logError(CORE,
              Some(s"Could not get decode html for doc  $docUrl"),
              dex)(id)
            r
          }
        }
      case Failure(ex) => {
        logError(
          CORE,
          Some(s"Could not get lift html for doc  $docUrl"),
          ex)(id)
        ex.getMessage
      }
    }

  }

}

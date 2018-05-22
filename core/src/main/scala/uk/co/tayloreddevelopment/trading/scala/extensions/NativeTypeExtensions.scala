package uk.co.tradingdevelopment.trading.scala.extensions

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import uk.co.tradingdevelopment.trading.scala.defaults.DefaultJsonMapper
import uk.co.tradingdevelopment.trading.scala.logging._
import uk.co.tradingdevelopment.trading.scala.extensions.GenericExtensions._
import uk.co.tradingdevelopment.trading.scala.extensions.GenericCollectionExtensions._
import scala.annotation.tailrec
import scala.util.Try
import uk.co.tradingdevelopment.trading.scala.defaults.JsonDefaults._
object NativeTypeExtensions extends Loggable {

  private lazy val nativeTypeExtensionsMapper = DefaultJsonMapper.get()

  implicit class StringExtensions(val obj: String) {
    def replaceSpecialCharacters(): String = {
      obj
        .replaceAll("\r\n", " ")
        .replaceAll("\n\r", " ")
        .replaceAll("\t", " ")

    }

    def recursivelyReplaceAll(toBeReplaced: String,
                              replacement: String): String = {

      @tailrec def innerRecursivelyReplaceAll(o: String,
                                              f: String,
                                              r: String): String =
        o.contains(toBeReplaced) match {
          case false => o
          case true  => innerRecursivelyReplaceAll(o.replaceAll(f, r), f, r)
        }

      innerRecursivelyReplaceAll(obj, toBeReplaced, replacement)
    }

    def replaceMarkers(id: String,
                       maps: List[(String, String)],
                       markers: (String, String) = ("@@", "@@")): String = {
      @tailrec
      def innerReplaceMarkers(payload: String,
                              marks: List[(String, String)]): String =
        marks match {
          case Nil => payload
          case x :: xs =>
            val replacementKey = markers._1 + x._1 + markers._2
            val newPayload = if (payload.contains(replacementKey)) {
              Try(payload.replace(replacementKey, x._2))
                .onErrorReturn(
                  payload,
                  e =>
                    logError(CORE,
                      Some(
                        "Error replacing key:" + replacementKey + " with value:" + x._2),
                      e))
            } else {
              payload
            }
            innerReplaceMarkers(newPayload, xs)
        }
      logInfo(CORE,
              Some("Replacing -> "),
              markers._1 + markers._2)
      innerReplaceMarkers(obj, maps)
    }

    def asJsonNode(key: String) = SimpleJsonNode(key, obj)
    def asJsonNode: JsonNode = nativeTypeExtensionsMapper.readTree(obj.toString)
    def asJsonArrayNode: ArrayNode = obj.asJsonNode.asInstanceOf[ArrayNode]
    def fromJSONString[A](id: String, obfuscate: Boolean)(
        implicit evidence: Manifest[A]): Try[A] =
      Try(nativeTypeExtensionsMapper.readValue[A](obj))

  }
  implicit class DoubleExtensions(n: Double) {
    import scala.math._
    def rounded(x: Int): Double = {
      val w = pow(10, x)
      (n * w).toLong.toDouble / w
    }
  }

  implicit class DoubleListExtensions(val list: List[Double]) {
    def avg(): Double = list.size match {
      case s if s > 0 => list.sum / list.size
      case _          => -1
    }
  }
  implicit class IntListExtensions(val list: List[Int]) {
    def avg(): Double = list.size match {
      case s if s > 0 => list.sum / list.size
      case _          => -1
    }
  }

}

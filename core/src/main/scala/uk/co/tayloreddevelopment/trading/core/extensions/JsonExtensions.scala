package uk.co.tradingdevelopment.trading.core.extensions

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import play.api.libs.json._
import uk.co.tradingdevelopment.trading.core.defaults.DefaultJsonMapper
import uk.co.tradingdevelopment.trading.core.defaults.JsonDefaults._
import scala.annotation.tailrec
import scala.util.Try

object JsonExtensions {

  private lazy val jsonExtensionsMapper = DefaultJsonMapper.get()

  implicit class ArrayNodeExtensions(val obj: ArrayNode) {

    def toVector[A](nodeFunc: JsonNode => A): Vector[A] =
      toVariousVector[A, A](nodeFunc, c => c)

    def toTryVector[A](nodeFunc: JsonNode => A): Vector[Try[A]] =
      toVariousVector[A, Try[A]](nodeFunc, c => Try(c))

    def toOptionVector[A](nodeFunc: JsonNode => A): Vector[Option[A]] =
      toVariousVector[A, Option[A]](nodeFunc, c => Try(c).toOption)

    private def toVariousVector[A, B](nodeFunc: JsonNode => A,
                                      c: A => B): Vector[B] = {
      @tailrec
      def innnerVector(acc: Vector[B],
                       i: java.util.Iterator[JsonNode],
                       f: JsonNode => A,
                       cs: A => B): Vector[B] = {
        if (i.hasNext) {
          innnerVector(cs(f(i.next())) +: acc, i, f, cs)
        } else {
          acc
        }
      }
      innnerVector(Vector.empty, obj.elements(), nodeFunc, c).reverse
    }

    def toList[A](nodeFunc: JsonNode => A): List[A] =
      toVariousList[A, A](nodeFunc, c => c)

    def toTryList[A](nodeFunc: JsonNode => A): List[Try[A]] =
      toVariousList[A, Try[A]](nodeFunc, c => Try(c))

    def toOptionList[A](nodeFunc: JsonNode => A): List[Option[A]] =
      toVariousList[A, Option[A]](nodeFunc, c => Try(c).toOption)

    private def toVariousList[A, B](nodeFunc: JsonNode => A,
                                    c: A => B): List[B] = {
      @tailrec
      def innnerList(acc: List[B],
                     i: java.util.Iterator[JsonNode],
                     f: JsonNode => A,
                     cs: A => B): List[B] = {
        if (i.hasNext) {
          innnerList(cs(f(i.next())) :: acc, i, f, cs)
        } else {
          acc
        }
      }
      innnerList(Nil, obj.elements(), nodeFunc, c).reverse
    }

    def getFieldFromFirstNode(field: String): JsonNode = obj.get(0).get(field)
    def getFieldFromFirstNodeAsText(field: String): String =
      obj.getFieldFromFirstNode(field).asText()

  }

  implicit class JsArrayExtensions(val obj: JsArray) {

    def asJsonArrayNode: ArrayNode =
      jsonExtensionsMapper.readTree(obj.toString()).asInstanceOf[ArrayNode]

    def getFieldFromFirstNode(field: String): JsLookupResult = obj.head \ field
    def getFieldFromFirstNodeAsText(field: String): String =
      obj.getFieldFromFirstNode(field).as[String]

    def toVector[A](nodeFunc: JsValue => A): Vector[A] =
      toVariousVector[A, A](nodeFunc, c => c)
    def toTryVector[A](nodeFunc: JsValue => A): Vector[Try[A]] =
      toVariousVector[A, Try[A]](nodeFunc, c => Try(c))

    def toOptionVector[A](nodeFunc: JsValue => A): Vector[Option[A]] =
      toVariousVector[A, Option[A]](nodeFunc, c => Try(c).toOption)

    private def toVariousVector[A, B](nodeFunc: JsValue => A,
                                      c: A => B): Vector[B] = {
      @tailrec
      def innnerVector(acc: Vector[B],
                       i: Vector[JsValue],
                       f: JsValue => A,
                       cs: A => B): Vector[B] = i match {
        case a if a.isEmpty => acc
        case x +: xs      => innnerVector(cs(f(x)) +: acc, xs, f, cs)
      }
      innnerVector(Vector.empty, obj.value.toVector, nodeFunc, c).reverse
    }

    def toList[A](nodeFunc: JsValue => A): List[A] =
      toVariousList[A, A](nodeFunc, c => c)
    def toTryList[A](nodeFunc: JsValue => A): List[Try[A]] =
      toVariousList[A, Try[A]](nodeFunc, c => Try(c))

    def toOptionList[A](nodeFunc: JsValue => A): List[Option[A]] =
      toVariousList[A, Option[A]](nodeFunc, c => Try(c).toOption)

    private def toVariousList[A, B](nodeFunc: JsValue => A,
                                    c: A => B): List[B] = {
      @tailrec
      def innnerList(acc: List[B],
                     i: Vector[JsValue],
                     f: JsValue => A,
                     cs: A => B): List[B] = i match {
        case a if a.isEmpty => acc
        case x +: xs      => innnerList(cs(f(x)) :: acc, xs, f, cs)
      }
      innnerList(Nil, obj.value.toVector, nodeFunc, c).reverse
    }

  }

  implicit class JsArrayVectorExtensions(val obj: Vector[JsArray]) {

    def concatenate(): JsArray = {
      var node = Json.arr()

      obj.foreach(an => {
        an.value.toVector.foreach(sn => node = node.append(sn))

      })
      node

    }

  }

  implicit class JsObjectVectorExtensions(val obj: Vector[JsObject]) {

    def concatenate(): JsArray = {
      var node = Json.arr()

      obj.foreach(an => {
        node = node.append(an)
      })
      node

    }

  }

  implicit class JsValueVectorExtensions(val obj: Vector[JsValue]) {
    @tailrec private def recAdd(vals: Vector[JsValue], arr: JsArray): JsArray =
      vals match {
        case Vector() => arr
        case x +: xs  => recAdd(xs, arr.append(x))
      }

    def concatenate(): JsArray = {
      var node = Json.arr()

      obj.foreach(an => {

        node = node.append(an)
      })
      node

    }

    def asJsArray: JsArray = recAdd(obj, Json.arr())
  }

}

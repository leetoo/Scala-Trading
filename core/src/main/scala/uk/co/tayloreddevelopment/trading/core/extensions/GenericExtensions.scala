package uk.co.tradingdevelopment.trading.core.extensions
import uk.co.tradingdevelopment.trading.core.defaults.JsonDefaults._
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import uk.co.tradingdevelopment.trading.core.defaults.DefaultJsonMapper

import scala.util.{Failure, Success, Try}

object GenericExtensions {
  private lazy val genericExtensionsMapper = DefaultJsonMapper.get()
  implicit class GenericTryExtensions[A](val obj: Try[A]) {

    def bimap[B](sF: A => B, eF: Throwable => Throwable): Try[B] = obj match {
      case Success(a)  => Try(sF(a))
      case Failure(ex) => Failure(eF(ex))

    }

    def to[B](sF: A => B, eF: Throwable => B): B = obj match {
      case Success(a)  => sF(a)
      case Failure(ex) => eF(ex)

    }

    def mapOnSuccess[B](f: A => B): Try[B] = obj match {
      case Success(a) => Try(f(a))
      case Failure(e) => Failure(e)
    }

    def onErrorReturn(finallyF: => A, ef: Exception => Unit): A = obj match {
      case Success(a) => a
      case Failure(ex) =>
        ef(ex.asInstanceOf[Exception])
        finallyF

    }

    def onError(ef: Exception => Unit): Try[A] = obj match {
      case Success(_) => obj
      case Failure(ex) =>
        ef(ex.asInstanceOf[Exception])
        obj
    }

    def getOrElseError(errorFunc: Exception => A): A = obj match {
      case Success(a)  => a
      case Failure(ex) => errorFunc(ex.asInstanceOf[Exception])
    }

    def processError(exF: Throwable => Unit): Unit = obj match {
      case Success(_)  => ()
      case Failure(ex) => exF(ex)
    }

  }

  implicit class GenericObjectExtensions[A](val obj: A) {

    def asJsonString: String = genericExtensionsMapper.writeValueAsString(obj)

    def asJsonNode: JsonNode =
      genericExtensionsMapper.readTree(obj.asJsonString)

    def asJsonArrayNode: ArrayNode =
      genericExtensionsMapper.readTree(obj.asJsonString).asInstanceOf[ArrayNode]

  }
}

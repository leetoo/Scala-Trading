package uk.co.tradingdevelopment.trading.core.extensions
import uk.co.tradingdevelopment.trading.core.defaults.JsonDefaults._
import com.fasterxml.jackson.databind.node.ArrayNode
import uk.co.tradingdevelopment.trading.core.defaults.DefaultJsonMapper
import uk.co.tradingdevelopment.trading.core.logging._

import scala.util.{Failure, Success, Try}

object GenericCollectionExtensions {

  private lazy val mapper = DefaultJsonMapper.get()

  implicit class GenericVectorTryExtensions[A](val obj: Vector[Try[A]]) {

    def onlySuccesses(): Vector[A] = obj collect { case Success(a)        => a }
    def onlyFailures(): Vector[Throwable] = obj collect { case Failure(e) => e }

  }

  implicit class GenericListTryExtensions[A](val obj: List[Try[A]]) {

    def onlySuccesses(): List[A] = obj collect { case Success(a)        => a }
    def onlyFailures(): List[Throwable] = obj collect { case Failure(e) => e }

  }

  implicit class GenericSeqTryExtensions[A](val obj: Seq[Try[A]]) {

    def onlySuccesses(): Seq[A] = obj collect { case Success(a)        => a }
    def onlyFailures(): Seq[Throwable] = obj collect { case Failure(e) => e }

  }

  implicit class GenericVectorExtensions[A](val list: Vector[A])
      extends Loggable {

    def distinctBy[B](selector: A => B): Vector[A] =
      list
        .map(selector)
        .distinct
        .map(di => list.filter(oi => di == selector(oi)).head)

    def asOption: Option[Vector[A]] = list match {
      case a if a.isEmpty => None
      case _            => Some(list)

    }

    def asTryArrayNode(id: String): Try[ArrayNode] = {
      try {
        val js = mapper.writeValueAsString(list)
        val tree = mapper.readTree(js)
        val o = tree.asInstanceOf[ArrayNode]
        Success(o)

      } catch {
        case ex: Exception =>
          Failure(
            logErrorAndReturn(CORE,
                              Some("Unable to convert object to array node"),
                              ex))

      }
    }

    def asArrayNode(id: String): ArrayNode = {
      list.asTryArrayNode(id) match {
        case Success(n) => n
        case Failure(ex) =>
          logError(CORE,
                   Some("Unable to parse as JsonArrayNode"),
                   ex)

          EmptyJsonArrayNode
      }
    }

  }

  implicit class GenericListExtensions[A](val list: List[A]) extends Loggable {

    def distinctBy[B](selector: A => B): List[A] = {
      val distinctList: List[B] = list.map(selector).distinct
      distinctList.map(di => list.filter(oi => di == selector(oi)).head)

    }

    def asOption: Option[List[A]] = list match {
      case Nil => None
      case _   => Some(list)

    }

    def asTryArrayNode(id: String): Try[ArrayNode] = {
      try {
        val js = mapper.writeValueAsString(list)
        val tree = mapper.readTree(js)
        val o = tree.asInstanceOf[ArrayNode]
        Success(o)

      } catch {
        case ex: Exception =>
          Failure(
            logErrorAndReturn(CORE,
                              Some("Unable to convert object to array node"),
                              ex))

      }
    }

    def asArrayNode(id: String): ArrayNode = {
      list.asTryArrayNode(id) match {
        case Success(n) => n
        case Failure(ex) =>
          logError(CORE,
                   Some("Unable to parse as JsonArrayNode"),
                   ex)

          EmptyJsonArrayNode
      }
    }

  }
}

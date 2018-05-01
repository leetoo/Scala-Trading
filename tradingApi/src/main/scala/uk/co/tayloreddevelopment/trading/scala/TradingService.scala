package uk.co.tradingdevelopment.trading.scala
import akka.NotUsed
import akka.stream.scaladsl.Source
import com.fasterxml.jackson.databind.JsonNode
import com.lightbend.lagom.scaladsl.api.Service._
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global


trait TradingService extends Service {
  def iamalive(): ServiceCall[NotUsed, Boolean]

  override def descriptor() =
    named("tradingservice")
      .withCalls(
        restCall(Method.GET, "/trading", iamalive _),
      )
      .withAutoAcl(true)



}
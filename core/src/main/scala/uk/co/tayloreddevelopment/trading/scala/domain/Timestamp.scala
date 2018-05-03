package uk.co.tradingdevelopment.trading.scala.domain

import java.time._

case class Timestamp(utc: Instant, system: Instant)
object Timestamp {
  def getCurrent: Timestamp =
    Timestamp(Instant.now(Clock.systemUTC()), Instant.now())


}

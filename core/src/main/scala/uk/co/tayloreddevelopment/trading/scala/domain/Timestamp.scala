package uk.co.tradingdevelopment.trading.scala.domain

import java.time._

case class Timestamp(utc: ZonedDateTime, system: ZonedDateTime)
object Timestamp {
  def getCurrent: Timestamp =
    Timestamp(ZonedDateTime.now(Clock.systemUTC()), ZonedDateTime.now())


}

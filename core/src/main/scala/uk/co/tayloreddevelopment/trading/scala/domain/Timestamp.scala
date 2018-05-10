package uk.co.tradingdevelopment.trading.scala.domain

import java.time._

import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}

case class Timestamp(utc: ZonedDateTime, system: ZonedDateTime)
object Timestamp {
  def getCurrent: Timestamp =
    Timestamp(ZonedDateTime.now(Clock.systemUTC()), ZonedDateTime.now())

  implicit val timestampHandler: BSONHandler[BSONDocument, Timestamp] =
    Macros.handler[Timestamp]
}

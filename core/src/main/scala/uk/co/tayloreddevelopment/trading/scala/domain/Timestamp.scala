package uk.co.tradingdevelopment.trading.scala.domain

import java.time._

import reactivemongo.bson.{BSONDocument, BSONHandler, BSONReader, BSONTimestamp, BSONWriter, Macros}

case class Timestamp(utc: Instant, system: Instant)
object Timestamp {
  def getCurrent: Timestamp =
    Timestamp(Instant.now(Clock.systemUTC()), Instant.now())


  implicit object InstantWriter extends BSONWriter[Instant, BSONTimestamp] {
    def write(datetime: Instant): BSONTimestamp = BSONTimestamp(datetime.toEpochMilli)
  }
  implicit object InstantReader extends BSONReader[ BSONTimestamp,Instant] {
    override def read(bson: BSONTimestamp): Instant = Instant.ofEpochMilli(bson.value)
  }


  implicit val timestampHandler: BSONHandler[BSONDocument, Timestamp] =
    Macros.handler[Timestamp]
}

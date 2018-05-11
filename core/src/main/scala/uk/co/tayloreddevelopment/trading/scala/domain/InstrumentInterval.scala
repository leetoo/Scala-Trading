package uk.co.tradingdevelopment.trading.scala.domain
import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}
import uk.co.tradingdevelopment.trading.scala.objects._

case class InstrumentInterval(interval: Interval, pricingType: PricingType)

object InstrumentInterval{
  implicit val intervalHandler: BSONHandler[BSONDocument, Interval] =
    Macros.handler[Interval]
  implicit val pricingTypeHandler: BSONHandler[BSONDocument, PricingType] =
    Macros.handler[PricingType]
  implicit val instrumentIntervalHandler: BSONHandler[BSONDocument, InstrumentInterval] =
    Macros.handler[InstrumentInterval]
}

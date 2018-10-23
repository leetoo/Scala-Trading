package uk.co.tradingdevelopment.trading.core.domain.objects
import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}
import uk.co.tayloreddevelopment.trading.scala.domain.{Interval, PricingType}


case class InstrumentInterval(interval: Interval, pricingType: PricingType)

object InstrumentInterval{
  implicit val intervalHandler: BSONHandler[BSONDocument, Interval] =
    Macros.handler[Interval]
  implicit val pricingTypeHandler: BSONHandler[BSONDocument, PricingType] =
    Macros.handler[PricingType]
  implicit val instrumentIntervalHandler: BSONHandler[BSONDocument, InstrumentInterval] =
    Macros.handler[InstrumentInterval]
}

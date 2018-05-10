package uk.co.tradingdevelopment.trading.scala.domain
import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}
import uk.co.tradingdevelopment.trading.scala.objects._

case class InstrumentInterval(interval: Interval, pricingType: PricingType)

object InstrumentInterval{
  implicit val instrumentIntervalHandler: BSONHandler[BSONDocument, InstrumentInterval] =
    Macros.handler[InstrumentInterval]
}

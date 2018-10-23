package uk.co.tradingdevelopment.trading.core.domain.objects

import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}

case class RiskRange(rangeStart:Double, rangeStop:Double, step:Double)

object RiskRange{
  implicit val riskRangeHandler: BSONHandler[BSONDocument, RiskRange] =
    Macros.handler[RiskRange]
}

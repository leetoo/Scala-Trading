package uk.co.tradingdevelopment.trading.core.domain.objects

import reactivemongo.bson.{BSONDocument, BSONHandler, BSONObjectID, Macros}
import uk.co.tayloreddevelopment.trading.scala.domain.Interval

case class Candle(id: BSONObjectID,
                  ticker: String,
                  underlying: String,
                  provider: String,
                  interval: Interval,
                  timeStamp: Timestamp,
                  open: Point,
                  high: Point,
                  low: Point,
                  volume: Volume)


object Candle{
//  implicit val candleHandler: BSONHandler[BSONDocument, Candle] =
//    Macros.handler[Candle]
}
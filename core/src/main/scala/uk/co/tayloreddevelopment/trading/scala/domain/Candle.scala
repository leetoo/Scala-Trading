package uk.co.tradingdevelopment.trading.scala.domain

import reactivemongo.bson.{BSONDocument, BSONHandler, BSONObjectID, Macros}
import uk.co.tradingdevelopment.trading.scala.objects.Interval

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
  implicit val candleHandler: BSONHandler[BSONDocument, Candle] =
    Macros.handler[Candle]
}
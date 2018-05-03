package uk.co.tradingdevelopment.trading.scala.domain

import reactivemongo.bson.BSONObjectID
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

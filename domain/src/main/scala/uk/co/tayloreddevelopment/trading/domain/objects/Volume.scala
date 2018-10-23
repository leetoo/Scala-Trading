package uk.co.tradingdevelopment.trading.core.domain.objects

import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}


case class Volume(volume:Double, tickVolume:Double)

object Volume{
  implicit val volumeHandler: BSONHandler[BSONDocument, Volume] =
    Macros.handler[Volume]
}

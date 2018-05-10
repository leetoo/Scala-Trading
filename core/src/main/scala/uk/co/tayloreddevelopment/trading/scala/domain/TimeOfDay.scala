package uk.co.tradingdevelopment.trading.scala.domain

import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}


case class TimeOfDay(hour:Int, minute:Int)

object TimeOfDay{
  implicit val timeOfDayHandler: BSONHandler[BSONDocument, TimeOfDay] =
    Macros.handler[TimeOfDay]
}

package uk.co.tradingdevelopment.trading.scala.domain

import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}


case class Watched(name:String, ticker:String)

object Watched{
  implicit val watchedHandler: BSONHandler[BSONDocument, Watched] =
    Macros.handler[Watched]
}

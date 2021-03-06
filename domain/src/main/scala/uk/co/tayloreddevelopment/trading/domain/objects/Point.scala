package uk.co.tradingdevelopment.trading.core.domain.objects

import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}

case class Point(bid:Double,ask:Double){
  def mid:Double = (bid,ask ) match {
    case (0.0,0.0) => 0
    case (b,0.0) => b
    case (0.0,a) => a
    case _ => (bid + ask)/2
  }
  def spread:Double = (bid,ask ) match {
    case (0.0,0.0) => 0
    case (b,0.0) => 0
    case (0.0,a) => 0
    case _ => bid-ask
  }
}

object Point{
  implicit val pointHandler: BSONHandler[BSONDocument, Point] =
    Macros.handler[Point]
}

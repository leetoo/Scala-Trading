package uk.co.tradingdevelopment.trading.scala.objects


import uk.co.tradingdevelopment.trading.scala.domain.Timestamp

sealed trait TimeInForce
case object GoodTillCancel extends TimeInForce
case object GoodForDay extends TimeInForce
case class GoodTillDate(time:Timestamp) extends TimeInForce


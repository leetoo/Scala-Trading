package uk.co.tradingdevelopment.trading.scala.objects

sealed trait Side

object Sides{
  case object Buy extends Side
  case object Sell extends Side
}





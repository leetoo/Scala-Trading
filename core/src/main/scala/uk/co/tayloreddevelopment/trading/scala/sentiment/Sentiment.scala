package uk.co.tradingdevelopment.trading.scala.sentiment

sealed trait Sentiment
case object VERY_NEGATIVE extends Sentiment
case object NEGATIVE extends Sentiment
case object NEUTRAL extends Sentiment
case object POSITIVE extends Sentiment
case object VERY_POSITIVE extends Sentiment
case object NOT_UNDERSTOOD extends Sentiment


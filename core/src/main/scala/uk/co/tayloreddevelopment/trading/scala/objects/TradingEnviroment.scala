package uk.co.tradingdevelopment.trading.scala.objects

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}


sealed trait TradingEnvironment

object TradingEnvironment{
  case object Live extends TradingEnvironment
  case object Demo extends TradingEnvironment
  case object Backtest extends TradingEnvironment

  implicit val decodeTradingEnvironment: Decoder[TradingEnvironment] = deriveEnumerationDecoder
  implicit val encodeTradingEnvironment: Encoder[TradingEnvironment] = deriveEnumerationEncoder

}


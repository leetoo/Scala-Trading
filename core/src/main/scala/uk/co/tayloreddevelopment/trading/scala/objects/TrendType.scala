package uk.co.tradingdevelopment.trading.scala.objects


import io.circe._
import io.circe.generic.extras.semiauto._
sealed trait TrendType

object TrendType{
  final case object Bullish extends TrendType
  final case object Bearish extends TrendType
  final case object Flat extends TrendType

  implicit val decodeContractType: Decoder[TrendType] = deriveEnumerationDecoder
  implicit val encodeContractType: Encoder[TrendType] = deriveEnumerationEncoder

}


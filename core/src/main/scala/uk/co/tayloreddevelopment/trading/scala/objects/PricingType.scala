package uk.co.tradingdevelopment.trading.scala.objects

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}


sealed trait PricingType

object PricingType{
  case object Stream extends PricingType
  case object Poll extends PricingType
  case object Derive extends PricingType

  implicit val decodePricingType: Decoder[PricingType] = deriveEnumerationDecoder
  implicit val encodePricingType: Encoder[PricingType] = deriveEnumerationEncoder
}


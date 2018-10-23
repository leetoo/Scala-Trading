package uk.co.tayloreddevelopment.trading.scala.domain

import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}
import io.circe.{Decoder, Encoder}


sealed trait PricingType

object PricingType{
  case object Stream extends PricingType
  case object Poll extends PricingType
  case object Derive extends PricingType

  implicit val decodePricingType: Decoder[PricingType] = deriveEnumerationDecoder
  implicit val encodePricingType: Encoder[PricingType] = deriveEnumerationEncoder


  import reactivemongo.bson.Macros
  import Macros.Options.{AutomaticMaterialization, UnionType, \/}

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedPricingType =
    UnionType[Stream.type \/ Poll.type \/ Derive.type ] with AutomaticMaterialization

  val predefinedPricingType = Macros.handlerOpts[PricingType, PredefinedPricingType]

}


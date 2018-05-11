package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}


sealed trait PricingType

object PricingType{
  case object Stream extends PricingType
  case object Poll extends PricingType
  case object Derive extends PricingType

  implicit val decodePricingType: Decoder[PricingType] = deriveEnumerationDecoder
  implicit val encodePricingType: Encoder[PricingType] = deriveEnumerationEncoder


  import reactivemongo.bson.Macros,
  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedPricingType =
    UnionType[Stream.type \/ Poll.type \/ Derive.type ] with AutomaticMaterialization

  val predefinedPricingType = Macros.handlerOpts[PricingType, PredefinedPricingType]

}


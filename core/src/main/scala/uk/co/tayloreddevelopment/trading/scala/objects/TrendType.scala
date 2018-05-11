package uk.co.tradingdevelopment.trading.scala.objects

import uk.co.tradingdevelopment.trading.scala.objects._
import io.circe._
import io.circe.generic.extras.semiauto._
sealed trait TrendType

object TrendType{
  final case object Bullish extends TrendType
  final case object Bearish extends TrendType
  final case object Flat extends TrendType

  implicit val decodeContractType: Decoder[TrendType] = deriveEnumerationDecoder
  implicit val encodeContractType: Encoder[TrendType] = deriveEnumerationEncoder

//
//  import reactivemongo.bson.Macros,
//  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }
//
//  // Use `UnionType` to define a subset of the `Color` type,
//  type PredefinedTrendType =
//    UnionType[Bullish.type \/ Bearish.type \/ Flat.type ] with AutomaticMaterialization
//
//  val predefinedTrendType = Macros.handlerOpts[TrendType, PredefinedTrendType]

}


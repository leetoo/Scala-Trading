package uk.co.tayloreddevelopment.trading.scala.domain

import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}
import io.circe.{Decoder, Encoder}

sealed trait InstrumentType

object InstrumentType{
  case object Index extends InstrumentType
  case object Currency extends InstrumentType
  case object Commodity extends InstrumentType
  case object Stock extends InstrumentType
  case object Bond extends InstrumentType
  case object Etf extends InstrumentType


  implicit val decodeInstrumentType: Decoder[InstrumentType] = deriveEnumerationDecoder
  implicit val encodeInstrumentType: Encoder[InstrumentType] = deriveEnumerationEncoder


  import reactivemongo.bson.Macros
  import Macros.Options.{AutomaticMaterialization, UnionType, \/}

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedInstrumentType =
    UnionType[Index.type \/ Currency.type \/ Commodity.type \/ Stock.type \/ Bond.type \/ Etf.type] with AutomaticMaterialization

  val predefinedInstrumentType = Macros.handlerOpts[InstrumentType, PredefinedInstrumentType]

}


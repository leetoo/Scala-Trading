package uk.co.tayloreddevelopment.trading.scala.domain

import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}
import io.circe.{Decoder, Encoder}

sealed trait ContractType

object ContractType{
  final case object Spreadbet extends ContractType
  final case object Cfd extends ContractType

  implicit val decodeContractType: Decoder[ContractType] = deriveEnumerationDecoder
  implicit val encodeContractType: Encoder[ContractType] = deriveEnumerationEncoder

  import reactivemongo.bson.Macros
  import Macros.Options.{AutomaticMaterialization, UnionType, \/}

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedContractType =
    UnionType[Spreadbet.type \/ Cfd.type ] with AutomaticMaterialization

  val predefinedContractType = Macros.handlerOpts[ContractType, PredefinedContractType]

}

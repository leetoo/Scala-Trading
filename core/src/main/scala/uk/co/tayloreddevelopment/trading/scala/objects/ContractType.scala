package uk.co.tradingdevelopment.trading.scala.objects

import com.msilb.scalandav20.model.instrument.CandlestickGranularity
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}
import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}
import uk.co.tradingdevelopment.trading.scala.domain.{ActionType, Notification, PlaceOrders}

sealed trait ContractType

object ContractType{
  final case object Spreadbet extends ContractType
  final case object Cfd extends ContractType

  implicit val decodeContractType: Decoder[ContractType] = deriveEnumerationDecoder
  implicit val encodeContractType: Encoder[ContractType] = deriveEnumerationEncoder

  import reactivemongo.bson.Macros,
  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedContractType =
    UnionType[Spreadbet.type \/ Cfd.type ] with AutomaticMaterialization

  val predefinedContractType = Macros.handlerOpts[ContractType, PredefinedContractType]

}

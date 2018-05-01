package uk.co.tradingdevelopment.trading.scala.objects

import com.msilb.scalandav20.model.instrument.CandlestickGranularity
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}

sealed trait ContractType

object ContractType{
  case object Spreadbet extends ContractType
  case object Cfd extends ContractType

  implicit val decodeContractType: Decoder[ContractType] = deriveEnumerationDecoder
  implicit val encodeContractType: Encoder[ContractType] = deriveEnumerationEncoder

}


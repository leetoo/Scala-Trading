package uk.co.tradingdevelopment.trading.scala.objects

import com.msilb.scalandav20.model.instrument.CandlestickGranularity
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}

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

}


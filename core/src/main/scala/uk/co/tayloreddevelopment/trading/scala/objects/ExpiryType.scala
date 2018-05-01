package uk.co.tradingdevelopment.trading.scala.objects

import com.msilb.scalandav20.model.instrument.CandlestickGranularity
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}

sealed trait ExpiryType

object ExpiryType{
  case object Cash extends ExpiryType
  case object Future extends ExpiryType

  implicit val decodeExpiryType: Decoder[ExpiryType] = deriveEnumerationDecoder
  implicit val encodeExpiryType: Encoder[ExpiryType] = deriveEnumerationEncoder

}





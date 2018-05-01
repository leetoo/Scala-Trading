package uk.co.tradingdevelopment.trading.scala.objects

import com.msilb.scalandav20.model.instrument.CandlestickGranularity
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}

sealed trait Side

object Side{
  case object Buy extends Side
  case object Sell extends Side

  implicit val decodeSide: Decoder[Side] = deriveEnumerationDecoder
  implicit val encodeSide: Encoder[Side] = deriveEnumerationEncoder

}





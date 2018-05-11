package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._
import com.msilb.scalandav20.model.instrument.CandlestickGranularity
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}

sealed trait Side

object Side{
  case object Buy extends Side
  case object Sell extends Side

  implicit val decodeSide: Decoder[Side] = deriveEnumerationDecoder
  implicit val encodeSide: Encoder[Side] = deriveEnumerationEncoder
//
//    import reactivemongo.bson.Macros,
//  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }
//
//  // Use `UnionType` to define a subset of the `Color` type,
//  type PredefinedSide =
//    UnionType[Buy.type \/ Sell.type ] with AutomaticMaterialization
//
//  val predefinedSide = Macros.handlerOpts[Side, PredefinedSide]

}





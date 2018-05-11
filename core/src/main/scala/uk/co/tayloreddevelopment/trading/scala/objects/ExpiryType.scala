package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._
import com.msilb.scalandav20.model.instrument.CandlestickGranularity
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}

sealed trait ExpiryType

object ExpiryType{
  case object Cash extends ExpiryType
  case object Future extends ExpiryType

  implicit val decodeExpiryType: Decoder[ExpiryType] = deriveEnumerationDecoder
  implicit val encodeExpiryType: Encoder[ExpiryType] = deriveEnumerationEncoder


  import reactivemongo.bson.Macros,
  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedExpiryType =
    UnionType[Cash.type \/ Future.type ] with AutomaticMaterialization

  val predefinedExpiryType = Macros.handlerOpts[ExpiryType, PredefinedExpiryType]

}





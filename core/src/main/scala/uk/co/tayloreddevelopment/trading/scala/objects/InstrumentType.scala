package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._
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


  import reactivemongo.bson.Macros,
  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedInstrumentType =
    UnionType[Index.type \/ Currency.type \/ Commodity.type \/ Stock.type \/ Bond.type \/ Etf.type] with AutomaticMaterialization

  val predefinedInstrumentType = Macros.handlerOpts[InstrumentType, PredefinedInstrumentType]

}


package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}


sealed trait TradingEnvironment

object TradingEnvironment{
  case object Live extends TradingEnvironment
  case object Demo extends TradingEnvironment
  case object Backtest extends TradingEnvironment

  implicit val decodeTradingEnvironment: Decoder[TradingEnvironment] = deriveEnumerationDecoder
  implicit val encodeTradingEnvironment: Encoder[TradingEnvironment] = deriveEnumerationEncoder


  import reactivemongo.bson.Macros,
  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedTradingEnvironment =
    UnionType[Live.type \/ Demo.type \/ Backtest.type ] with AutomaticMaterialization

  val predefinedTradingEnvironment = Macros.handlerOpts[TradingEnvironment, PredefinedTradingEnvironment]

}


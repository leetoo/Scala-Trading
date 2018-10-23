package uk.co.tayloreddevelopment.trading.scala.domain

import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}
import io.circe.{Decoder, Encoder}


sealed trait TradingEnvironment

object TradingEnvironment{
  case object Live extends TradingEnvironment
  case object Demo extends TradingEnvironment
  case object Backtest extends TradingEnvironment

  implicit val decodeTradingEnvironment: Decoder[TradingEnvironment] = deriveEnumerationDecoder
  implicit val encodeTradingEnvironment: Encoder[TradingEnvironment] = deriveEnumerationEncoder


  import reactivemongo.bson.Macros
  import Macros.Options.{AutomaticMaterialization, UnionType, \/}

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedTradingEnvironment =
    UnionType[Live.type \/ Demo.type \/ Backtest.type ] with AutomaticMaterialization

  val predefinedTradingEnvironment = Macros.handlerOpts[TradingEnvironment, PredefinedTradingEnvironment]

}


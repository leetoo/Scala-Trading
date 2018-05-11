package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._

import uk.co.tradingdevelopment.trading.scala.domain.Timestamp

sealed trait TimeInForce
case object GoodTillCancel extends TimeInForce
case object GoodForDay extends TimeInForce
case class GoodTillDate(time:Timestamp) extends TimeInForce

object TimeInForce{
//  import reactivemongo.bson.Macros,
//  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }
//
//  // Use `UnionType` to define a subset of the `Color` type,
//  type PredefinedTimeInForce =
//    UnionType[GoodTillCancel.type \/ GoodForDay.type \/ GoodTillDate ] with AutomaticMaterialization
//
//  val predefinedTimeInForce = Macros.handlerOpts[TimeInForce, PredefinedTimeInForce]

}
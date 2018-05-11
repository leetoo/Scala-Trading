package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._


sealed trait IndicatorOperator
case object Gt extends IndicatorOperator
case object Gte extends IndicatorOperator
case object Eq extends IndicatorOperator
case object Lt extends IndicatorOperator
case object Lte extends IndicatorOperator

object IndicatorOperator{
//  import reactivemongo.bson.Macros,
//  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }
//
//  // Use `UnionType` to define a subset of the `Color` type,
//  type PredefinedIndicatorOperator =
//    UnionType[Gt.type \/ Gte.type \/ Eq.type \/ Lt.type \/ Lte.type ] with AutomaticMaterialization
//
//  val predefinedIndicatorOperator = Macros.handlerOpts[IndicatorOperator, PredefinedIndicatorOperator]

}
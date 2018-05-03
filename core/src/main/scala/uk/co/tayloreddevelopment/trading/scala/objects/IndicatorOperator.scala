package uk.co.tradingdevelopment.trading.scala.objects



sealed trait IndicatorOperator
case object Gt extends IndicatorOperator
case object Gte extends IndicatorOperator
case object Eq extends IndicatorOperator
case object Lt extends IndicatorOperator
case object Lte extends IndicatorOperator


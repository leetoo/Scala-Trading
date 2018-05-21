package uk.co.tradingdevelopment.trading.scala.logging

sealed trait Component extends Product with Serializable
final case object BACKTEST extends Component
final case object DEAL extends Component
final case object EVALUATION extends Component
final case object ORDER extends Component
final case object PRICING extends Component
final case object STRATEGY extends Component
final case object TRADING extends Component
final case object VALUATION extends Component
final case object NEWS extends Component
final case object CORE extends Component


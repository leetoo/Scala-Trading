package uk.co.tradingdevelopment.trading.core.logging

sealed trait LogLevel extends Product with Serializable
final case object INFO extends LogLevel
final case object ERROR extends LogLevel
final case object TIMING extends LogLevel
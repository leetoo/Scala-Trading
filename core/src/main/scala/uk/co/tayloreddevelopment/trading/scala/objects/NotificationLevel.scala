package uk.co.tradingdevelopment.trading.scala.objects

import uk.co.tradingdevelopment.trading.scala.domain.Timestamp

sealed trait NotificationLevel
case object AlertLevel extends NotificationLevel
case object InfoLevel extends NotificationLevel
case object ErrorLevel extends NotificationLevel

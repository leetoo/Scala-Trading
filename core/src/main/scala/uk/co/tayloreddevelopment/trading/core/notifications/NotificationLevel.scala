package uk.co.tradingdevelopment.trading.core.notifier


sealed trait NotificationLevel
case object High extends NotificationLevel
case object Normal extends NotificationLevel
case object Low extends NotificationLevel
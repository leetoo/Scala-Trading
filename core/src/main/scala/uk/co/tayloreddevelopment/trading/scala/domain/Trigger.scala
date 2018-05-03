package uk.co.tradingdevelopment.trading.scala.domain

import java.time._

import uk.co.tradingdevelopment.trading.scala.domain.Timestamp

sealed trait Trigger
case class TimeOfDayTrigger(timeOfDay: TimeOfDay) extends Trigger {
  require(timeOfDay.hour <= 23 && timeOfDay.minute <= 59)
}
case class TimeOfRangeTrigger(startTime: TimeOfDay, endTime: TimeOfDay)
    extends Trigger
case class DayOfWeekTrigger(dayOfWeek: Int) extends Trigger {
  require(dayOfWeek <= 7)
}
case class DayOfMonthTrigger(dayOfMonth: Int) extends Trigger {
  require(dayOfMonth <= 31)
}
case class CronTrigger(expression:String) extends Trigger


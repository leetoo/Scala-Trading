package uk.co.tradingdevelopment.trading.core.domain.triggers

import java.time.temporal.ChronoField
import java.time.{LocalDateTime, LocalTime}

import uk.co.tayloreddevelopment.trading.scala.domain.Interval
import uk.co.tradingdevelopment.trading.core.collections.SlidingStack
import uk.co.tradingdevelopment.trading.core.domain.objects.{Candle, TimeOfDay}

import scala.util.Try

case class TimeOfDayRangeTrigger(ticker: String,
                            prov: String,
                            instInterval: Interval,
                                 startTime: TimeOfDay, endTime: TimeOfDay)
    extends Trigger {
  require(startTime.hour <= 23 && startTime.minute <= 59 && endTime.hour <= 23 && endTime.minute <= 59)
  override val interval: Interval = instInterval
  override var candles: SlidingStack[Candle] = new SlidingStack[Candle](1)
  override val instrument: String = ticker
  override val provider: String = provider

  override def valuation(stack: SlidingStack[Candle]): Boolean =
    Try {
      val c = stack.get.head
      val cTime= c.timeStamp.system
      val cDateTime = LocalTime.of(cTime.get(ChronoField.HOUR_OF_DAY),cTime.get(ChronoField.MINUTE_OF_HOUR))
      val startDateTime =  LocalTime.of(startTime.hour,startTime.minute)
      val endDateTime =   LocalTime.of(endTime.hour,endTime.minute)
      startDateTime.isBefore(cDateTime) &&  endDateTime.isAfter(cDateTime)

    }.getOrElse(false)
}

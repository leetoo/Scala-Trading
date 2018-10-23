package uk.co.tradingdevelopment.trading.core.domain.triggers

import java.time.temporal.{ChronoField}

import uk.co.tayloreddevelopment.trading.scala.domain.Interval
import uk.co.tradingdevelopment.trading.core.collections.SlidingStack
import uk.co.tradingdevelopment.trading.core.domain.objects.{Candle, TimeOfDay}

import scala.util.Try

case class TimeOfDayTrigger(ticker: String,
                            prov: String,
                            instInterval: Interval,
                            timeOfDay: TimeOfDay)
    extends Trigger {
  require(timeOfDay.hour <= 23 && timeOfDay.minute <= 59)
  override val interval: Interval = instInterval
  override var candles: SlidingStack[Candle] = new SlidingStack[Candle](1)
  override val instrument: String = ticker
  override val provider: String = provider

  override def valuation(stack: SlidingStack[Candle]): Boolean =
    Try {
      val c = stack.get.head
      val t = c.timeStamp.system

      (t.get(ChronoField.HOUR_OF_DAY), t.get(ChronoField.MINUTE_OF_HOUR)) match {
        case (h, m)
            if timeOfDay.hour == h && timeOfDay.minute == m =>
          true
        case _ => false

      }
    }.getOrElse(false)
}

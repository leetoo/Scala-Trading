package uk.co.tayloreddevelopment.trading.scala.trigger

import java.time.temporal.TemporalField

import uk.co.tradingdevelopment.trading.scala.collections.SlidingStack
import uk.co.tradingdevelopment.trading.scala.domain.{Candle, TimeOfDay}
import uk.co.tradingdevelopment.trading.scala.objects.Interval

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

      (t.getHour, t.getMinute) match {
        case (h, m)
            if timeOfDay.hour == h && timeOfDay.minute == m =>
          true
        case _ => false

      }
    }.getOrElse(false)
}
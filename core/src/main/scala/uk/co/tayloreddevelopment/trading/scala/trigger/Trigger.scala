package uk.co.tayloreddevelopment.trading.scala.trigger

import uk.co.tayloreddevelopment.trading.scala.domain.Interval
import uk.co.tradingdevelopment.trading.scala.collections.SlidingStack
import uk.co.tradingdevelopment.trading.scala.domain._
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._
trait Trigger {
  val interval: Interval
  val instrument:String
  val provider:String
  var candles: SlidingStack[Candle]
  def valuation(stack: SlidingStack[Candle]): Boolean


  def process(c: Candle): Boolean = (c.interval,c.provider,c.ticker) match {
    case (i,p,t) if i == interval && p==provider && t == instrument =>
                                                                          candles = candles.put(c)
                                                                          valuation(candles)
    case _ => false
  }
}



//case class DayOfWeekTrigger(dayOfWeek: Int) extends Trigger {
//  require(dayOfWeek <= 7)
//}
//case class DayOfMonthTrigger(dayOfMonth: Int) extends Trigger {
//  require(dayOfMonth <= 31)
//}
//case class CronTrigger(expression: String) extends Trigger

package uk.co.tradingdevelopment.trading.core.domain.triggers

import uk.co.tayloreddevelopment.trading.scala.domain.Interval
import uk.co.tradingdevelopment.trading.core.collections.SlidingStack
import uk.co.tradingdevelopment.trading.core.domain._
import uk.co.tradingdevelopment.trading.core.domain.objects.Candle
import uk.co.tradingdevelopment.trading.core.operators.Pipe._
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

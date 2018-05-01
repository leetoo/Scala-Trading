
package uk.co.tradingdevelopment.trading.scala.objects
sealed trait Interval

object Intervals{
  case object Tick extends Interval
  case object M1 extends Interval
}

package uk.co.tradingdevelopment.trading.scala.operators


case class Ifte(b: Boolean) {
  def ?[X](t: => X) = new {
    def |[X](f: => X) = if(b) t else f
  }
}

object Ifte {
  implicit def IfThenElse(b: Boolean) = Ifte(b)
}
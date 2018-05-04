package uk.co.tradingdevelopment.trading.scala.operators


case class Pipe[T](o: T) {
  def |>[S](f: T => S): S = f(o)
}

object Pipe{
  implicit def PipeThis[T](o: T) = Pipe(o)
}
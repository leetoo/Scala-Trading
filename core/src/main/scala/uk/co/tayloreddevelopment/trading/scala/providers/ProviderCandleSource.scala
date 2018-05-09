package uk.co.tradingdevelopment.trading.scala.providers

import akka.NotUsed
import akka.stream.scaladsl.Source
import uk.co.tradingdevelopment.trading.scala.domain.Candle


trait ProviderSource{
  def getCandleSource:Source[NotUsed,Candle]

}
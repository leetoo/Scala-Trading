package uk.co.tradingdevelopment.trading.scala.providers
import akka.NotUsed
import akka.stream.scaladsl.Source
import uk.co.tradingdevelopment.trading.scala.domain.{Candle, Provider}

object ProviderFactory{


  def getCandleSource(providerSpecs:Vector[Provider]):Vector[Source[NotUsed,Candle]] = {
    providerSpecs.map(p => p.name match {
      case "OANDA" => OandaProviderSource.getCandleSource
      case _ =>  OandaProviderSource.getCandleSource



    })


  }

}
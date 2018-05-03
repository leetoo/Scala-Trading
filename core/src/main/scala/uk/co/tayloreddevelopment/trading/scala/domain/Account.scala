package uk.co.tradingdevelopment.trading.scala.domain

import uk.co.tradingdevelopment.trading.scala.objects.{
  ContractType,
  TradingEnvironment
}


case class Account(alias: String,
                   ProviderName: String,
                   authDetails: Map[String, String],
                   isForPricing: Boolean,
                   environment: TradingEnvironment,
                   contractType: ContractType)

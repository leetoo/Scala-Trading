package uk.co.tradingdevelopment.trading.scala.domain

case class OrderSpecification(entry:Option[RiskRange],exit:Option[RiskRange], stop:Option[RiskRange])
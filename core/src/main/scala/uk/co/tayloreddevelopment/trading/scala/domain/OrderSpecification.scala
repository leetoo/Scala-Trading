package uk.co.tradingdevelopment.trading.scala.domain

import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}

case class OrderSpecification(entry:Option[RiskRange],exit:Option[RiskRange], stop:Option[RiskRange])


object OrderSpecification{
  implicit val orderSpecificationHandler: BSONHandler[BSONDocument, OrderSpecification] =
    Macros.handler[OrderSpecification]
}


package uk.co.tradingdevelopment.trading.scala.domain


import reactivemongo.bson._

case class Provider(id:BSONObjectID= BSONObjectID.generate,
                    name:String,
                    accounts:Vector[Account],
                    durations:Vector[InstrumentInterval],
                    instruments:Vector[Instrument])


object Provider{
  implicit val personHandler: BSONHandler[BSONDocument, Provider] =
    Macros.handler[Provider]
}
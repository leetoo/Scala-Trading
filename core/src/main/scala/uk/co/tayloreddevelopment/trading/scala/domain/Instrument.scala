package uk.co.tradingdevelopment.trading.scala.domain
import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}
import uk.co.tradingdevelopment.trading.scala.objects._

case class Instrument(ticker: String,
                      underlying: String,
                      instrumentType: InstrumentType,
                      expiryType: ExpiryType,
                      contractType: ContractType)


object Instrument{
  implicit val contractTypeHandler: BSONHandler[BSONDocument, ContractType] =
    Macros.handler[ContractType]
  implicit val expiryTypeHandler: BSONHandler[BSONDocument, ExpiryType] =
    Macros.handler[ExpiryType]
  implicit val instrumentTypeHandler: BSONHandler[BSONDocument, InstrumentType] =
    Macros.handler[InstrumentType]

  implicit val instrumentHandler: BSONHandler[BSONDocument, Instrument] =
    Macros.handler[Instrument]
}
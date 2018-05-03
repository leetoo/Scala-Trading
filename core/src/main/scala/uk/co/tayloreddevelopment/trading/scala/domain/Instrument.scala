package uk.co.tradingdevelopment.trading.scala.domain
import uk.co.tradingdevelopment.trading.scala.objects._

case class Instrument(ticker: String,
                      underlying: String,
                      instrumentType: InstrumentType,
                      expiryType: ExpiryType,
                      contractType: ContractType)

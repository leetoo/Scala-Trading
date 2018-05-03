
package uk.co.tradingdevelopment.trading.scala.domain

import reactivemongo.bson.BSONObjectID
import uk.co.tradingdevelopment.trading.scala.objects.Interval


case class Provider(id:BSONObjectID,
                    name:String,
                    accounts:Vector[Account],
                    durations:List[InstrumentInterval],
                    instruments:List[Instrument])


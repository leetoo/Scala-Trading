package uk.co.tradingdevelopment.trading.scala.persistence

import reactivemongo.bson.BSONDocument


trait MongoCollection[A] {

  protected def serialize(item:A) : BSONDocument
  protected def deserialize(doc:BSONDocument):A

  


}
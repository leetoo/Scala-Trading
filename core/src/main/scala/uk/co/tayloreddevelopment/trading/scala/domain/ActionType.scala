package uk.co.tradingdevelopment.trading.scala.domain


import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}
import uk.co.tradingdevelopment.trading.scala.domain._
import uk.co.tradingdevelopment.trading.scala.objects._

sealed trait ActionType
case class PlaceOrders(orders:List[OrderSpecification]) extends ActionType
case class Notification(level:NotificationLevel,subject:String,message:String) extends ActionType


object ActionType{
  implicit val placeOrders = Macros.handler[PlaceOrders]
  implicit val notification = Macros.handler[Notification]
  implicit val bson: BSONHandler[BSONDocument, ActionType] = Macros.handler[ActionType]
}
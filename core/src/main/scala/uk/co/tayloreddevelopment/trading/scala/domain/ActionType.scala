package uk.co.tradingdevelopment.trading.scala.domain


import uk.co.tradingdevelopment.trading.scala.domain._
import uk.co.tradingdevelopment.trading.scala.objects._

sealed trait ActionType
case class PlaceOrders(orders:List[OrderSpecification]) extends ActionType
case class Notification(level:NotificationLevel,subject:String,message:String) extends ActionType

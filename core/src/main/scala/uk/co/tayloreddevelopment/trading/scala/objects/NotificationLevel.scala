package uk.co.tradingdevelopment.trading.scala.objects
import uk.co.tradingdevelopment.trading.scala.objects._
import uk.co.tradingdevelopment.trading.scala.domain.Timestamp

sealed trait NotificationLevel
case object AlertLevel extends NotificationLevel
case object InfoLevel extends NotificationLevel
case object ErrorLevel extends NotificationLevel
object NotificationLevel{
//  import reactivemongo.bson.Macros,
//  Macros.Options.{ AutomaticMaterialization, UnionType, \/ }
//
//  // Use `UnionType` to define a subset of the `Color` type,
//  type PredefinedNotificationLevel =
//    UnionType[AlertLevel.type \/ InfoLevel.type \/ ErrorLevel.type ] with AutomaticMaterialization
//
//  val predefinedNotificationLevel = Macros.handlerOpts[NotificationLevel, PredefinedNotificationLevel]

}
package uk.co.tradingdevelopment.trading.scala.objects

import java.time.{DayOfWeek, LocalDate}

import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._

 case class Calendar(id:String, name:String,activeDays:Vector[DayOfWeek],holidays:Vector[LocalDate])

//object Calendar {
//  def canTrade(date:LocalDate) :Boolean= !holidays.contains(date)
//}
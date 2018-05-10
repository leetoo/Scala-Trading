package uk.co.tradingdevelopment.trading.scala.domain

import java.time.{DayOfWeek, LocalDate}

import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._
import reactivemongo.bson.{BSONDocument, BSONHandler, Macros}

 case class Calendar(id:String, name:String,activeDays:Vector[DayOfWeek],holidays:Vector[LocalDate])
{
  def canTrade(date:LocalDate) :Boolean= !holidays.contains(date)
}


object Calendar{
  implicit val calendarHandler: BSONHandler[BSONDocument, Calendar] =
    Macros.handler[Calendar]
}
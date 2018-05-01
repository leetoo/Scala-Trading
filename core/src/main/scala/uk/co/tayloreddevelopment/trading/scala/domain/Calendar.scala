package uk.co.tradingdevelopment.trading.scala.objects

import java.time.{DayOfWeek, LocalDate}

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._


case class Calendar(id:String, name:String,activeDays:Vector[DayOfWeek],holidays:Vector[LocalDate]){

  def canTrade(date:LocalDate) :Boolean= !holidays.contains(date)

  implicit val decodeCalendar: Decoder[Calendar] = deriveDecoder
  implicit val encodeCalendar: Encoder[Calendar] = deriveEncoder
}
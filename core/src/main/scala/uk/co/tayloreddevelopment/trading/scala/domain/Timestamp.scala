package uk.co.tradingdevelopment.trading.scala.objects

import java.time
import java.time.ZonedDateTime

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._


case class Timestamp(utc:ZonedDateTime, system:ZonedDateTime) {

  implicit val decodeTimestamp: Decoder[Timestamp] = deriveDecoder
  implicit val encodeTimestamp: Encoder[Timestamp] = deriveEncoder
}
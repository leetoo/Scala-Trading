package uk.co.tradingdevelopment.trading.scala.collections

import java.time.LocalDateTime

import scala.collection.immutable.Vector

case class HashStamp(date: LocalDateTime, hash: Int)

class HashValidator[A](expiryMinutes: Int, historyScale:Int) {

  var hashList = Vector.empty[HashStamp]

  def validate(item: A): Option[A] = {
    val timeStamp = LocalDateTime.now()
    val reduced = hashList.filterNot(_.date.plusMinutes(expiryMinutes * historyScale).isBefore(timeStamp))
    val hash = item.hashCode()
    hashList.map(i => i.hash).contains(hash) match {
      case true => None
      case false => {
        hashList = HashStamp(timeStamp, hash) +: reduced
        Some(item)
      }
    }

  }

}

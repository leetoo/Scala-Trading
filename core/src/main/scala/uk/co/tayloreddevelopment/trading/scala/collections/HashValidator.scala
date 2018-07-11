package uk.co.tradingdevelopment.trading.scala.collections

import java.time.LocalDateTime

import scala.collection.immutable.Vector
import java.security.MessageDigest
case class HashStamp(date: LocalDateTime, hash: String)

class HashValidator[A](expiryMinutes: Int, historyScale:Int, hasher:Option[A => String]) {

  private def getHash(item:A) = MessageDigest.getInstance("MD5").digest(item.toString.getBytes)

  var hashList = Vector.empty[HashStamp]

  def validate(item: A): Option[A] = {
    val timeStamp = LocalDateTime.now()
    val reduced = hashList.filterNot(_.date.plusMinutes(expiryMinutes * historyScale).isBefore(timeStamp))
    val hash: String =hasher match {
      case Some(h) => h(item)
      case None => getHash(item).map(_.toChar).mkString
    }
    hashList.map(i => i.hash).contains(hash) match {
      case true => None
      case false => {
        println(s"$hash not found adding to hash list")
        hashList = HashStamp(timeStamp, hash) +: reduced
        println(s"$hash not found adding to hash list " + hashList)
        Some(item)
      }
    }

  }

}

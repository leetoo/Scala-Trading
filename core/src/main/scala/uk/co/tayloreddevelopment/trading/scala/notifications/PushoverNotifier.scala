package uk.co.tradingdevelopment.trading.scala.notifier

import net.pushover.client.{MessagePriority, PushoverMessage, PushoverRestClient}

import scala.util.Try
// API Token ar246pddo2bhgn4c6axx4648dm4tue
// USER Token gnpdagjyiam46hge3b6a4srt535jk2
class PushoverNotifier(apiToken:String,userToken:String) extends Notifier {

  private val client = new PushoverRestClient()

  private def getLevel(level:NotificationLevel):MessagePriority = level match {
    case High => MessagePriority.HIGH
    case Normal => MessagePriority.NORMAL
    case Low => MessagePriority.QUIET
  }

  override def Notify(title: String, message: String,url:Vector[String], level:NotificationLevel): Unit = client.pushMessage(PushoverMessage.builderWithApiToken(apiToken)
    .setUserId(userToken)
    .setMessage(message)
    .setTitle(title)
      .setUrl(Try(url.head).getOrElse(""))


    .setPriority(getLevel(level))
    .setSound("magic")
    .build())
}
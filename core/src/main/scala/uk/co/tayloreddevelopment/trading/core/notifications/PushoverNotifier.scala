package uk.co.tradingdevelopment.trading.core.notifier

import net.pushover.client.{MessagePriority, PushoverMessage, PushoverRestClient}

import scala.util.Try
// API Token ar246pddo2bhgn4c6axx4648dm4tue
// USER Token gnpdagjyiam46hge3b6a4srt535jk2
class PushoverNotifier(apiToken: String, userToken: String) extends Notifier {

  private val client = new PushoverRestClient()

  private def getLevel(level: NotificationLevel): MessagePriority =
    level match {
      case High   => MessagePriority.HIGH
      case Normal => MessagePriority.NORMAL
      case Low    => MessagePriority.QUIET
    }

  override def Notify(title: String,
                      message: String,
                      url: Option[Vector[String]],
                      level: NotificationLevel): Unit = {

    var msgBase = PushoverMessage
      .builderWithApiToken(apiToken)
      .setUserId(userToken)
      .setMessage(message)
      .setTitle(title)
      .setPriority(getLevel(level))
      .setSound("magic")

    url match {
      case Some(u) => msgBase.setUrl(Try(u.head).getOrElse("")).build()
      case None    => client.pushMessage(msgBase.build())
    }

  }

  override def Notify[A](itemToLog: A,
                         transform: A => (String,
                           String,
                           Option[Vector[String]],
                           NotificationLevel)): Unit = {
    val processedMsg = transform(itemToLog)
    Notify(processedMsg._1, processedMsg._2, processedMsg._3, processedMsg._4)
  }
}

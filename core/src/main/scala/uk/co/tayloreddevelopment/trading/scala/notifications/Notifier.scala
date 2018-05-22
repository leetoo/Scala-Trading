package uk.co.tradingdevelopment.trading.scala.notifier


trait Notifier {
  def Notify(title:String, message:String, level:NotificationLevel)


}
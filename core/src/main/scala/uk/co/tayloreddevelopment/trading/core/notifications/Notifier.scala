package uk.co.tradingdevelopment.trading.core.notifier


trait Notifier {
  def Notify(title: String,
             message: String,
             url: Option[Vector[String]],
             level: NotificationLevel)
  def Notify[A](itemToLog: A,
                transform: A => (String,
                  String,
                  Option[Vector[String]],
                  NotificationLevel))

}

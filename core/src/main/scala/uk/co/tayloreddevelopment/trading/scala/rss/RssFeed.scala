package uk.co.tradingdevelopment.trading.scala.rss


trait RssFeed {
  val link:String
  val title:String
  val desc:String
  val items:Seq[RssItem]
  override def toString = title + "\n" + desc + "\n**"

  def latest = items sortWith ((a, b) => a.date.compareTo(b.date) > 0) head
}
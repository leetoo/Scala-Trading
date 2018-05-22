package uk.co.tradingdevelopment.trading.scala.sentiment

trait SentimentProcessor {
  def getSentiment(text:String):Sentiment

}

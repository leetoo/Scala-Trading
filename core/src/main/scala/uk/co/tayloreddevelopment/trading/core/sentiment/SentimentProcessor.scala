package uk.co.tradingdevelopment.trading.core.sentiment

trait SentimentProcessor {
  def getSentiment(text:String):Sentiment

}

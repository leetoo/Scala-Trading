package uk.co.tradingdevelopment.trading.scala.sentiment


class NaiveSentiment  extends SentimentProcessor {
  override def getSentiment(text: String): Sentiment = text.toLowerCase().contains("excellent") match {
    case true => POSITIVE
    case _ => NEUTRAL
  }
}
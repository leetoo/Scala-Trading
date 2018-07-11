package uk.co.tradingdevelopment.trading.scala.sentiment

import uk.co.tradingdevelopment.trading.scala.sentiment.sse._

class SseSentiment  extends SentimentProcessor {
  lazy val wordList = WordListLoader.loadDefault()
  lazy val sseSentiment = new SentimentAnalyzer(wordList)

  override def getSentiment(text: String): Sentiment ={
    val sent = sseSentiment.sentimentOf(text)
    val numberofWords:Double = sent.negativeWords.size.toDouble + sent.positiveWords.size.toDouble
val scaledScore:Double = sent.score.toDouble / numberofWords.toDouble

    scaledScore  match {
      case s if s < -1.0 => VERY_NEGATIVE
      case s if s < 0.0 => NEGATIVE
      case s if s == 0.0 => NEUTRAL
      case s if s > 0.0 => POSITIVE
      case s if s > 1.0 => VERY_POSITIVE
      case _ => NOT_UNDERSTOOD

    }
  }
}
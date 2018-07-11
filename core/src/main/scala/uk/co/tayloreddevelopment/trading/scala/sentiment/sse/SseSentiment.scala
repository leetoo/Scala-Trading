package uk.co.tradingdevelopment.trading.scala.sentiment.sse

case class SseSentiment(
                      score: Int,
                      numberOfWords: Int,
                      positiveWords: Set[String],
                      negativeWords: Set[String]) {

  def comparative: Float = score.toFloat / numberOfWords
}
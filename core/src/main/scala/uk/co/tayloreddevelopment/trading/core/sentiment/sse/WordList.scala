
package uk.co.tradingdevelopment.trading.core.sentiment.sse
class WordList(words: Map[String, Int]) {

  private val wordsMap = words.withDefaultValue(0)

  def valenceOf(word: String): Int = wordsMap(word)

  def + (word: (String, Int)) = {
    new WordList(words + word)
  }

  def ++ (words: Map[String, Int]) = {
    new WordList(this.wordsMap ++ words)
  }

  def ++ (wordList: WordList) = {
    new WordList(this.wordsMap ++ wordList.wordsMap)
  }
}

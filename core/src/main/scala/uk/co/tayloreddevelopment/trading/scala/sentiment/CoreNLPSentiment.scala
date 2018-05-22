package uk.co.tradingdevelopment.trading.scala.sentiment

import java.util.Properties
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

class CoreNLPSentiment() extends SentimentProcessor {
  override def getSentiment(text: String): Sentiment = {

    val pipeline = new StanfordCoreNLP(new Properties().|>(p => {
      p.setProperty("annotators",
        "tokenize, ssplit, pos, lemma, parse, sentiment"); p
    }))
    val annotation = pipeline.process(text)

    lazy val sentimentsAndSizes: List[(Int, Int)] = annotation
      .get(classOf[CoreAnnotations.SentencesAnnotation])
      .map(s =>
        (s, s.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])))
      .map(t =>
        (t._1.toString.length, RNNCoreAnnotations.getPredictedClass(t._2)))
      .toList

    lazy val mainSentimentAndLongest: (Int, Int) = sentimentsAndSizes
      .map(_._1)
      .max
      .|>(m => sentimentsAndSizes.filter(t => t._1 == m).head)

    lazy val totalSize = sentimentsAndSizes
      .map(_._1)
      .sum

    lazy val weightedSentiment = sentimentsAndSizes
      .map(x => x._1 * x._2)
      .|>(ws => ws.sum / totalSize)

    val mainAndWeighted = sentimentsAndSizes.map(_._1).size match {
      case x if x == 0 => (-1, -1)
      case _ => (mainSentimentAndLongest._1, weightedSentiment)
    }

    mainAndWeighted._2 match {
      case s if s <= 0.0 => return NOT_UNDERSTOOD
      case s if s < 1.0 => return VERY_NEGATIVE
      case s if s < 2.0 => return NEGATIVE
      case s if s < 3.0 => return NEUTRAL
      case s if s < 4.0 => return POSITIVE
      case s if s < 5.0 => return VERY_POSITIVE
      case s if s > 5.0 => return NOT_UNDERSTOOD
    }


  }
}
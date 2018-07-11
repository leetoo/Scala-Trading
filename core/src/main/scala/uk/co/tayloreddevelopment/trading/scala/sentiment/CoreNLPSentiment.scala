package uk.co.tradingdevelopment.trading.scala.sentiment

import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import edu.stanford.nlp.util.CoreMap
import uk.co.tradingdevelopment.trading.scala.logging.Perf

class CoreNLPSentiment() extends SentimentProcessor {

  override def getSentiment(text: String): Sentiment = {

    val props = new Properties()
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    val pipeline = new StanfordCoreNLP(props)
    print(Perf.GetPerformance)

    val annotation = new Annotation(text)
    pipeline.annotate(annotation)
    print(Perf.GetPerformance)
    val sentences = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
    val sentence: CoreMap = sentences.get(0)
    val sentiment = sentence.get(classOf[SentimentCoreAnnotations.SentimentClass])
    // Uncomment to see sentiment score
    // println(s"${tweet} -> ${sentiment}")
    print(Perf.GetPerformance)
    println(sentiment)
    NEUTRAL
//    sentiment match {
//      case s if s <= 0.0 => return NOT_UNDERSTOOD
//      case s if s < 1.0 => return VERY_NEGATIVE
//      case s if s < 2.0 => return NEGATIVE
//      case s if s < 3.0 => return NEUTRAL
//      case s if s < 4.0 => return POSITIVE
//      case s if s < 5.0 => return VERY_POSITIVE
//      case s if s > 5.0 => return NOT_UNDERSTOOD
//    }


  }
}
package uk.co.tayloreddevelopment.trading.scala.twitter

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.streaming.StreamingMessage
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import uk.co.tayloreddevelopment.trading.scala.twitter.TwitterStreamer._



object TwitterStreamer{
  type ConsumerKey = String
  type ConsumerSecret = String
  type AccessKey = String
  type AccessSecret = String

}
class TwitterStreamer(consumerKey:ConsumerKey,
                      consumerSecret:ConsumerSecret,
                      accessKey:AccessKey,
                      accessSecret:AccessSecret,
                      processFunction:PartialFunction[StreamingMessage, Unit]){
  val consumerToken = ConsumerToken(key = consumerKey, secret = consumerSecret)
  val accessToken = AccessToken(key = accessKey, secret = accessSecret)

  val client = TwitterStreamingClient(consumerToken, accessToken)

  client.sampleStatuses(stall_warnings = true)(processFunction)

}
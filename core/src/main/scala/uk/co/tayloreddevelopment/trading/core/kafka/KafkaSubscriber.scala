package uk.co.tradingdevelopment.trading.core.kafka

import java.util
import java.util.Arrays
import java.util.Properties

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
class KafkaSubscriber(servers: KafkaServers,
                      topic: String,
                      poolTimeout: Long,
                      processor: ConsumerRecord[String,String] => Unit) {

  var subscribing = true

  val props = new Properties()
  println(servers.kafkaEndpoints)
  props.put("bootstrap.servers", servers.kafkaEndpoints)
  println(servers.zookeeperEndpoints)
  props.put("zookeeper.connect", servers.zookeeperEndpoints)
  props.put("group.id", "fraXses")
  props.put("key.deserializer", classOf[StringDeserializer])
  props.put("value.deserializer", classOf[StringDeserializer])

  val consumer = new KafkaConsumer[String, String](props)
  println("Created Kafka Consumer")
  consumer.subscribe(util.Collections.singletonList(topic))
  println("Subscribed Kafka Consumer")
  def stop() = {
    subscribing = false
    Thread.sleep(100)
    consumer.unsubscribe()
    consumer.close()
  }

  def run(): Future[Boolean] = Future {

    while (subscribing) {
      val records = consumer.poll(poolTimeout).asScala
      println(records.size + "received" )
      for (record <- records) {
        processor(record)
      }
    }

    true
  }

}

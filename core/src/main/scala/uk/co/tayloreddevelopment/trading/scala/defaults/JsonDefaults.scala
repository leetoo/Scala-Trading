package uk.co.tradingdevelopment.trading.scala.defaults

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.{
  ArrayNode,
  JsonNodeFactory,
  ObjectNode
}
import play.api.libs.json._
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._

object JsonDefaults {
  private lazy val mapper = DefaultJsonMapper.get()
  private val factory = JsonNodeFactory.instance
  val EmptyJsonArrayNode: ArrayNode = new ArrayNode(factory)
  val EmptyJsonNode: JsonNode = mapper.readTree("{}")
  val EmptyJsArray: JsArray = Json.arr()

  def FailureNode(ex: Exception): JsonNode = {
    val node = new ArrayNode(factory)
    node.add(
      mapper.readTree("{\"exceptionMessage\":\"" + ex.getMessage + "\"}"))
    node

  }
  def FailureNodeArray(ex: Exception): ArrayNode = {
    new ArrayNode(factory)
      .|>(en => en.add(FailureNode(ex)))
  }

  def SimpleMultiValueJsonNode(
      keyValuePairs: Vector[(String, String)]): JsonNode = {
    val n: ObjectNode = mapper.readTree("{}").asInstanceOf[ObjectNode]
    keyValuePairs.foreach(kvp => n.put(kvp._1, kvp._2))
    n.asInstanceOf[JsonNode]
  }

  def SimpleJsonNode(key: String, value: String): JsonNode =
    mapper.readTree("{\"" + key + "\":\"" + value + "\"}")
  def SimpleJsonArrayNode(key: String, value: String): ArrayNode = {
    val node = new ArrayNode(factory)
    node.add(SimpleJsonNode(key, value))
    node
  }

  def SingleKeyValueJsObject(key: String, value: String) =
    JsObject(Seq(key -> JsString(value)))

  def SingleKeyValueJsArray(nodes: Map[String, String]): JsArray = {
    var node: JsArray = Json.arr()
    for (elem <- nodes) {
      val obj = SingleKeyValueJsObject(elem._1, elem._2)
      node = node.append(obj)
    }
    node
  }

  def JoinToArrayNode(nodes: List[JsonNode]): ArrayNode = {
    val node = new ArrayNode(factory)
    nodes.foreach(n => node.add(n))
    node

  }

  def JsonNodeWithArrayOrResults(key: String, arr: ArrayNode): JsonNode = {
    val n: ObjectNode = mapper.readTree("{}").asInstanceOf[ObjectNode]
    n.put(key, arr)

    n.asInstanceOf[JsonNode]
  }

  def TableArrayNode(headers: List[String],
                     rows: List[List[String]]): ArrayNode =
    JoinToArrayNode(rows.map(r => MappedJsonNode(headers.zip(r))))

  def MappedJsonNode(kvp: List[(String, String)]): JsonNode = {
    val n: ObjectNode = mapper.readTree("{}").asInstanceOf[ObjectNode]

    for (elem <- kvp) {
      n.put(elem._1, elem._2)
    }
    n.asInstanceOf[JsonNode]
  }
}

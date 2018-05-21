package uk.co.tradingdevelopment.trading.scala.logging

object Port {
  import java.io.File

  import play.api.{Configuration, Environment, Mode}
  private lazy val config = Configuration
    .load(Environment(new File("."), getClass.getClassLoader, Mode.Prod))
    .underlying
  lazy val port: Int = {
    try {
      config.getInt("play.server.http.port")
    } catch {
      case _: Exception => 0
    }
  }

}
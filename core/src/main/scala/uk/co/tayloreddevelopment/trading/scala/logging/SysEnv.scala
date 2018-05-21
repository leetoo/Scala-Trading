package uk.co.tradingdevelopment.trading.scala.logging

import scala.util.Try


case class SysEnv(name: Option[String],
                  host: Option[String],
                  ip: Option[String])

object SysEnv {
  private lazy val user = System.getProperty("user.name")
  private lazy val machine = java.net.InetAddress.getLocalHost
  private lazy val machineName = machine.getHostName

  def GetEnvironment: SysEnv = {
    SysEnv(Try(user).toOption,
      Try(machineName).toOption,
      Try(machine.getHostAddress).toOption)
  }
}
package uk.co.tradingdevelopment.trading.core.logging

import scala.util.Try


case class BuildEnv(version: Option[String],
                    scalaVersion: Option[String],
                    sbtVersion: Option[String])

object BuildEnv {

  def GetBuild: BuildEnv = {
    BuildEnv(
      Try(fraxses.gateway.version.BuildInfo.version).toOption,
      Try(fraxses.gateway.version.BuildInfo.scalaVersion).toOption,
      Try(fraxses.gateway.version.BuildInfo.sbtVersion).toOption
    )
  }
}

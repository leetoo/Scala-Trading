package uk.co.tradingdevelopment.trading.scala

import com.softwaremill.macwire._
import com.google.inject.AbstractModule
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}

import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.status.MetricsServiceComponents
import com.lightbend.lagom.scaladsl.server.{ LagomApplication, LagomApplicationContext, LagomApplicationLoader }
import com.softwaremill.macwire._
import com.typesafe.conductr.bundlelib.lagom.scaladsl.ConductRApplicationComponents
import play.api.libs.ws.ahc.AhcWSComponents


import play.api.{Configuration, Environment}
import play.api.libs.ws.ahc.AhcWSComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.status.MetricsServiceComponents


abstract class TradingApplication(context: LagomApplicationContext)
  extends LagomApplication(context) with AhcWSComponents
    with MetricsServiceComponents {

  override lazy val lagomServer = serverFor[TradingService](wire[TradingServiceImpl])

}


class TradingLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication = new TradingApplication(context) with ConductRApplicationComponents
  override def describeService = Some(readDescriptor[TradingService])
}
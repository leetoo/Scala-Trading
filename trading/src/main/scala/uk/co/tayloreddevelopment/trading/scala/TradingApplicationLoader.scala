package uk.co.tradingdevelopment.trading.core


import com.lightbend.lagom.scaladsl.server.{ LagomApplication, LagomApplicationContext, LagomApplicationLoader }
import com.lightbend.lagom.scaladsl.server.status.MetricsServiceComponents
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import play.api.libs.ws.ahc.AhcWSComponents
import com.softwaremill.macwire._

abstract class TradingApplication(context: LagomApplicationContext)
  extends LagomApplication(context) with AhcWSComponents
    with MetricsServiceComponents {

  override lazy val lagomServer = serverFor[TradingService](wire[TradingServiceImpl])

}


class TradingLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication = new TradingApplication(context) {
    override def serviceLocator: ServiceLocator = NoServiceLocator
  }
  override def describeService = Some(readDescriptor[TradingService])
}

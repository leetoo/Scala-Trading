package uk.co.tradingdevelopment.trading.core

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import javax.inject.Inject
import play.api.Environment
import play.api.inject.ApplicationLifecycle

import scala.concurrent.{ExecutionContext, Future}

class TradingServiceImpl @Inject()(environment: Environment,
                                lifecycle: ApplicationLifecycle)(implicit ec: ExecutionContext)  extends TradingService {
  override def iamalive(): ServiceCall[NotUsed, Boolean] =  ServiceCall { _ => Future.successful(true)}
}
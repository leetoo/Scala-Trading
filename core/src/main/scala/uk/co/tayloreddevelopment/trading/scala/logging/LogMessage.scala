package uk.co.tradingdevelopment.trading.scala.logging

import org.joda.time.DateTime

import org.slf4j.{Logger, LoggerFactory}
import uk.co.tradingdevelopment.trading.scala.extensions.GenericExtensions._
import uk.co.tradingdevelopment.trading.scala.extensions.GenericCollectionExtensions._
import uk.co.tradingdevelopment.trading.scala.extensions.JsonExtensions._
import uk.co.tradingdevelopment.trading.scala.extensions.NativeTypeExtensions._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode

import play.api.libs.json.{JsArray, JsObject}

trait LogObfuscatable[T] {
  def obfuscation(t: T): T

  def obfuscateEntity(): T = this.obfuscation(this.asInstanceOf[T])

}
trait Loggable {
  private lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def logInfo[A](component: Component,
                 message: Option[String],
                 logItem: A) =
    log( INFO, component, message, logItem)
  def logInfoAndReturn[A](component: Component,
                          message: Option[String],
                          logItem: A) =
    logAndReturn( INFO, component, message, logItem)
  def logError[A](component: Component,
                  message: Option[String],
                  logItem: A) =
    log( ERROR, component, message, logItem)
  def logErrorAndReturn[A](component: Component,
                           message: Option[String],
                           logItem: A) =
    logAndReturn( ERROR, component, message, logItem)
  def logTiming[A]( component: Component,
                   message: Option[String],
                   logItem: A) =
    log( TIMING, component, message, logItem)
  def logTimingAndReturn[A](component: Component,
                            message: Option[String],
                            logItem: A) =
    logAndReturn(TIMING, component, message, logItem)

  private def logAndReturn[A](level: LogLevel,
                              component: Component,
                              message: Option[String],
                              logItem: A) = {
    log(level, component, message, logItem)
    logItem
  }

  def logAsIO[A](
                 level: LogLevel,
                 component: Component,
                 message: Option[String],
                 logItem: A,
                 timestamp: DateTime,
                 stack: Array[StackTraceElement]): Future[Unit] =
    Future {

      val ct = stack(7)
      val methodName = ct.getMethodName
      val lineNumber = ct.getLineNumber
      val className = ct.getClassName
      val fileName = ct.getFileName

      val m = LogMessage[A](
        level,
        component,
        fileName,
        className,
        methodName,
        lineNumber.toString,
        message,
        logItem,
        timestamp)

      val flatLog = LogWrapper(DateTime.now.toString,
        m.toFlatLogMessage,
        Perf.GetPerformance,
        SysEnv.GetEnvironment,
        BuildEnv.GetBuild)

      Try(flatLog.toString) match {
        case Success(logItem) =>
          if (m.level == ERROR) {
            Try(logger.error(logItem)).processError(println)
          } else {
            Try(logger.info(logItem)).processError(println)
          }
        case Failure(ex) =>
          Try(logger.error(ex.getLocalizedMessage)).processError(println)

      }

    }

  def log[A](
             level: LogLevel,
             component: Component,
             message: Option[String],
             logItem: A): Unit = {
    val t = new DateTime()
    val stack: Array[StackTraceElement] = Thread.currentThread().getStackTrace()
    logAsIO(level, component, message, logItem, t, stack)
  }

}

case class LogWrapper(timestamp: String,
                      log: FlatLogMessage,
                      performance: Perf,
                      environment: SysEnv,
                      build: BuildEnv)

case class LogMessage[A](
                         level: LogLevel,
                         component: Component,
                         fileName: String,
                         area: String,
                         method: String,
                         lineNumber: String,
                         message: Option[String],
                         logItem: A,
                         when: DateTime) {

  def toFlatLogMessage: FlatLogMessage = {
    val paredLogItem: String = try {

      message.toString
    } catch {
      case _: Exception => logItem.toString
    }

    FlatLogMessage(
                   level,
      component,
                   Port.port,
                   fileName,
      area,
                   method,
                   lineNumber,
                   message,
      paredLogItem,
                   this.when.getMillis())
  }
}
case class FlatLogMessage(level: LogLevel,
                          component: Component,
                          port: Int,
                          fileName: String,
                          area: String,
                          method: String,
                          lineNumber: String,
                          message: Option[String],
                          logItem: String,
                          when: Long)


package uk.co.tradingdevelopment.trading.core.logging
import scala.util.Try

case class Perf(processors: Option[Int],
                total: Option[Long],
                free: Option[Long],
                used: Option[Long],
                max: Option[Long],
                usedPerc: Option[Long])

object Perf {
  lazy val mb: Long = 1024 * 1024
  def GetPerformance: Perf = {
    val runtime = Runtime.getRuntime
    val used = runtime.totalMemory - runtime.freeMemory
    val total = runtime.totalMemory
    val free = runtime.freeMemory
    val max = runtime.maxMemory
    Perf(
      Try(runtime.availableProcessors()).toOption,
      Try(total / mb).toOption,
      Try(free / mb).toOption,
      Try(used / mb).toOption,
      Try(max / mb).toOption,
      Try(used / total).toOption
    )

  }

}
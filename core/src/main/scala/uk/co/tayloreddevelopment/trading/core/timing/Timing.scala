package uk.co.tradingdevelopment.trading.core.timing


object Timing {

  def time[R](block: =>R): (Long, R) = {
    val start = System.currentTimeMillis
    val result = block
    (System.currentTimeMillis - start, result)
  }

  def time[R](block: =>R, handler: Long=>Any): R = {
    val (ms, result) = time(block)
    handler(ms)
    result
  }

  def printTime[R](block: =>R) {
    time(block, println)
  }


  class Stats(val total: Long, val max: Long, val min: Long, val avg: Long) {
    override def toString =
      "total: " + total + "ms,  min: " + min +
        "ms,  max: " + max + "ms,  avg: " + avg + "ms"
  }


  def speedTest(executions: List[() => Any]) {
    val times = executions.map(e => time({ e () })._1)

    val total = times.reduce(_+_)
    val max = times.reduce(math.max(_, _))
    val min = times.reduce(math.min(_, _))
    val avg = total / executions.size

    new Stats(total, max, min, avg)
  }
}
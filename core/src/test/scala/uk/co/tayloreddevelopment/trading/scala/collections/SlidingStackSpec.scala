package uk.co.tradingdevelopment.trading.scala.collections
import uk.co.tradingdevelopment.trading.scala.timing._
import uk.co.tradingdevelopment.trading.scala.operators.Pipe._
import org.scalatest._
import Matchers._
class ExampleSpec extends FunSpec {

  describe("A SlidingStack") {

    it("should remain a constant size when adding") {
      new SlidingStack[Int](4)
        .|>(b => b.put(5))
        .|>(b => b.put(4))
        .|>(b => b.put(3))
        .|>(b => b.put(2))
        .|>(b => b.put(1))
        .|>(b => b.get.size shouldBe (4))

    }

    it("should be fast for addition") {
      new SlidingStack[Int](4)
        .|>(tb =>
          Timing.time {
            tb.put(10)
              .|>(b => b.put(9))
              .|>(b => b.put(8))
              .|>(b => b.put(7))
              .|>(b => b.put(6))
              .|>(b => b.put(5))
              .|>(b => b.put(4))
              .|>(b => b.put(3))
              .|>(b => b.put(2))
              .|>(b => b.put(1))

        })
        .|>(b => {
          b._1.toLong should be < 10.toLong

          b._2.get should equal(Vector(1, 2, 3, 4))
        })

    }

    it("should lose the final value when a new value is added") {
       new SlidingStack[Int](4)
         .|>(b => b.put(5))
         .|>(b => b.put(4))
         .|>(b => b.put(3))
         .|>(b => b.put(2))
         .|>(b => b.put(1))
         .|>(b => b.get should equal(Vector(1, 2, 3, 4)))

    }
  }
}

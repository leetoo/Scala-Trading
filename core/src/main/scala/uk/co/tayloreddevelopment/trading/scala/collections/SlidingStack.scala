
package uk.co.tradingdevelopment.trading.scala.collections


class SlidingStack[T](val lim:Int){
  var data =Vector.empty[T]

  def get:Vector[T] = data

  def put(o:T):SlidingStack[T] = {

    def removeLast(values:Vector[T], acc:Vector[T]):Vector[T] = values.tail.isEmpty match {
    case true => acc
    case false =>removeLast(values.tail,acc :+ values.head)
    }

    data.size >= lim match {
      case true => data = removeLast(o +: this.get, Vector.empty[T])
      case false => data = o +: data
    }


    this
  }
}

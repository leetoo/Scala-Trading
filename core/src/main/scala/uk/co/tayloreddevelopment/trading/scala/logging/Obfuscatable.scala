
package uk.co.tradingdevelopment.trading.scala.logging

trait Obfuscatable[T] {
  def obfuscation(t: T): T

  def obfuscateEntity(): T = this.obfuscation(this.asInstanceOf[T])

}

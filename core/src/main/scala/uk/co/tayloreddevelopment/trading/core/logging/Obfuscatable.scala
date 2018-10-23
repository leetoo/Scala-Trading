
package uk.co.tradingdevelopment.trading.core.logging

trait Obfuscatable[T] {
  def obfuscation(t: T): T

  def obfuscateEntity(): T = this.obfuscation(this.asInstanceOf[T])

}

package uk.co.tayloreddevelopment.trading.scala.domain

import cats.Eval
import io.circe.generic.extras.semiauto.{deriveEnumerationDecoder, deriveEnumerationEncoder}
import io.circe.{Decoder, Encoder}

sealed trait Interval

object Interval {
  case object Tick extends Interval
  case object M1 extends Interval
  case object M5 extends Interval
  case object M15 extends Interval
  case object M30 extends Interval
  case object H1 extends Interval
  case object H2 extends Interval
  case object H3 extends Interval
  case object H4 extends Interval
  case object H6 extends Interval
  case object H8 extends Interval
  case object D1 extends Interval
  case object D5 extends Interval
  case object W1 extends Interval

  import reactivemongo.bson.Macros
  import Macros.Options.{AutomaticMaterialization, UnionType, \/}

  // Use `UnionType` to define a subset of the `Color` type,
  type PredefinedInterval =
    UnionType[Tick.type \/ M1.type \/ M5.type \/ M15.type \/ M30.type \/ H1.type \/ H2.type \/ H3.type \/ H4.type \/ H6.type \/ H8.type  \/ D1.type \/ D5.type \/ W1.type] with AutomaticMaterialization

  val predefinedInterval = Macros.handlerOpts[Interval, PredefinedInterval]



  implicit val decodeInterval: Decoder[Interval] = deriveEnumerationDecoder
  implicit val encodeInterval: Encoder[Interval] = deriveEnumerationEncoder

  def minutesToInterval(minutes:Int):Eval[Interval] = secondsToInterval(minutes*60)

  def secondsToInterval(seconds: Int): Eval[Interval] = seconds match {
    case 1      => Eval.now(Tick)
    case 60     => Eval.now(M1)
    case 300    => Eval.now(M5)
    case 900    => Eval.now(M15)
    case 1800   => Eval.now(M30)
    case 3600   => Eval.now(H1)
    case 7200   => Eval.now(H2)
    case 10800  => Eval.now(H3)
    case 14400  => Eval.now(H4)
    case 21600  => Eval.now(H6)
    case 28800  => Eval.now(H8)
    case 86400  => Eval.now(D1)
    case 432000 => Eval.now(D5)
    case 604800 => Eval.now(W1)
    case _      => Eval.now(Tick)

  }

  def intervalToMinutes(int: Interval): Eval[Int] = intervalToSeconds(int).map(x => x/60)

  def intervalToSeconds(int: Interval): Eval[Int] = int match {

    case M1  => Eval.now(60)
    case M5  => Eval.now(5 * 60)
    case M15 => Eval.now(15 * 60)
    case M30 => Eval.now(30 * 60)
    case H1  => Eval.now(60 * 60)
    case H2  => Eval.now(2 * 60 * 60)
    case H3  => Eval.now(3 * 60 * 60)
    case H4  => Eval.now(4 * 60 * 60)
    case H6  => Eval.now(5 * 60 * 60)
    case H8  => Eval.now(6 * 60 * 60)
    case D1  => Eval.now(24 * 60 * 60)
    case D5  => Eval.now(5 * 24 * 60 * 60)
    case W1  => Eval.now(7 * 24 * 60 * 60)
    case _   => Eval.now(1)
  }
}






//type indexName = FTSE | DOWJONES | SP500 | NASDAQ | RUSSELL2000 | DAX | CAC | MIB |IBEX | STXE | SMI | BUX | MEX |SAF | ASX |  NIKKEI | HANGSEN | NIFTY | BOVESPA | OMX | FANG | HSCHIN
//type fxName = GBPSEK | PLNJPY |  NICKEL | GBPDKK | NZDCNH | SEKJPY | BITCOIN | XBTGBP | NZDCAD | EURTRY | GBPMXN | GBPTRY | NZDEUR | EURMXN | USDNOK | USDMXN | GBPNZD | GBPCHF | USDTRY | USCGC | EURNZD | EURUSD | GBPUSD | USDJPY | AUDUSD | EURJPY |EURGBP | USDCAD | GBPEUR | USDCHF | EURCHF | USDOLLAR | GBPJPY | GBPCAD | USDZAR | GBPZAR | XAUUSD | XAGUSD | GBPAUD | AUDJPY | NZDUSD
//type commodityName = COCOA | COFFEE | NATGAS | OIL | GOLD | SILVER | GASOIL | HEATOIL | COPPER | NICKEL | ZINC | LUMBER | OJ
package uk.co.tayloreddevelopment.trading.scala.news.actors

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.{DefaultDB, MongoConnection}
import uk.co.tayloreddevelopment.trading.scala.twitter.TwitterItem
import uk.co.tradingdevelopment.trading.scala.domain.Watched
import  uk.co.tayloreddevelopment.trading.scala.news.actors.messages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class TagFilterActor extends Actor {
  protected lazy val conf: Config = ConfigFactory.load()

  lazy val tagsItems = Vector(
    //Watched("Bitcoin","BTC"),
    //Watched("Etherium","ETH"),
    //Watched("Litecoin","LTC"),
    Watched("Ocado","OCDO"),
    Watched("Sainsbury","SBRY"),
    Watched("Tesco","TSCO"),
    Watched("Standard Life","SLA"),
    Watched("Lloyds Banking Group","LLOY"),
    Watched("Royal Bank of Scotland","RBS"),
    Watched("Barclays","BARC"),
    Watched("HSBC Holdings","HSBA"),
    Watched("Hargreaves Lansdown","HL."),
    Watched("Standard Chartered ","STAN"),
    Watched("Learning Technologies Group","LTG"),
    Watched("Evraz","EVR"),
    Watched("Burford Capital","BUR"),
    Watched("Fevertree Drinks","FEVR"),
      Watched("KAZ Minerals","KAZ"),
    Watched("Next Fifteen Communications","NFC"),
    Watched("Rentokil","RTO"),
    Watched("NMC Health","NMC"),
    Watched("Concepta","CPT"),
    Watched("Management Consulting Group","MMC"),
    Watched("Nasstar","NASA"),
    Watched("Pelatro","PTRO"),
    Watched("Tri-Star Resources","TSTR"),
    Watched("Realm Therapeutics","RLM"),
    Watched("Mobile Tornado Group","MBT"),
    Watched("Oracle Power","ORCP"),
    Watched("Elektron Technology","EKT"),
      Watched("Tekcapital","TEK"),
    Watched("London Security","LSC"),
    Watched("Minds + Machines Group","MMK"),
    Watched("Vertu Motors","VTU"),
    Watched("Faron Pharmaceuticals","FARN"),
    Watched("Slingsby","SLNG"),
    Watched("BT Group","BT"),
    Watched("Stobart ","STOB"),
    Watched("Trinity Exploration & Production","TRIN"),
    Watched("Talktalk","TALK"),
    Watched("Wetherspoon","JDW"),
    Watched("ContourGloba","GLO"),
    Watched("Grafton","GFTU"),
    Watched("Superdry","SDRY"),
    Watched("Clarkson","CKN"),
    Watched("Hikma","HIK"),
    Watched("Kier Group","KIE"),
    Watched("Renewables Infrastructure Group","TRIG"),
    Watched("Syncona","SYNC"),
    Watched("Card Factory","CARD"),
    Watched("Sports Direct","SPD"),
    Watched("Softcat","SCT"),
    Watched("Indivior","INDV"),
    Watched("Ferrexpo","FXPO"),
    Watched("TP ICAP","TCAP"),
    Watched("Thomas Cook","TCG"),
    Watched("Wood Group","WG."),
    Watched("Premier Oil","PMO"),
    Watched("Tullow Oil","TLW"),
    Watched("Vietnam Enterprise Investments","VEIL"),
    Watched("Weir Group","WEIR"),
    Watched("Cairn Energy","CNE"),
    Watched("Provident Financial","PFG"),
    Watched("Playtech","PTEC"),
    Watched("Alfa Financial Software","ALFA"),
    Watched("Dixons Carphone","DC."),
    Watched("CYBG","CYBG"),
    Watched("Man Group","EMG"),
    Watched("Virgin Money","VM."),
    Watched("Computacenter","CCC"),
    Watched("Inmarsat","ISAT"),
    Watched("Capita","CPI"),
    Watched("ContourGlobal","GLO"),
    Watched("William Hill","WMH"),
    Watched("Ted Baker","TED"),
    Watched("Autotrader","AUTO"),
    Watched("Entertainment One","ETO"),
    Watched("Intu Properties","INTU"),

    Watched("Moneysupermarket","MONY"),
    Watched("Interserve","IRV"),
    Watched("Rank","RNK"),
    Watched("Speedy Hire","SDY"),
    Watched("Babcock","BAB"),
    Watched("Circassia Pharmaceuticals","CIR"),
    Watched("Carpetright","CPR"),
    Watched("Debenhams ","DEB")



  )




 lazy val tags = tagsItems.flatMap(f => Vector(s" ${f.name}.",s" ${f.name} ",s" ${f.ticker}.L", s"#${f.ticker} ","$" + f.ticker + " "))

  val linkProcessingActor = this.context.actorOf(Props[LinkProcessingActor])
  override def receive: Receive = {
    case x: Filter =>tags.foreach(tg => x.text.toLowerCase.contains(tg.toLowerCase()) match {
      case true =>  linkProcessingActor ! ProcessLinks(x.key,tg, x.source, x.text)
      case false => ()
    })
    case _ => println("I don't know how to process this message")
  }
}

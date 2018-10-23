/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
import sbt.Keys.libraryDependencies
import sbt._

object Dependencies {

  private val sparkVersion = "2.2.0"

  private val circeVersion = "0.9.3"

  //Core

  val macros = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
  val catsCore = "org.typelevel" %% "cats-core" % "1.0.1"
  val fs2Core = "co.fs2" %% "fs2-core" % "0.10.1"
  val fs2Io = "co.fs2" %% "fs2-io" % "0.10.1"
  val shapeless = "com.chuusai" %% "shapeless" % "2.3.3"
  val catsEffect = "org.typelevel" %% "cats-effect" % "1.0.0-RC"
  val rxKafkaCore = "io.bfil" %% "rx-kafka-core" % "0.2.0"
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.12"
  val circeCore = "io.circe" %% "circe-core" % circeVersion
  val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  val circeParser = "io.circe" %% "circe-parser" % circeVersion
  val scalactic = "org.scalactic" %% "scalactic" % "3.0.0"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.0"
  val rxScala = "io.reactivex" %% "rxscala" % "0.26.5"
  val rome = "rome" % "rome" % "1.0"
  val twitter4s =  "com.danielasfregola" %% "twitter4s" % "5.5"
  val catsFree = "org.typelevel" %% "cats-free" % "1.0.1"
  val monix = "io.monix" %% "monix" % "3.0.0-RC1"



//Json
  val jacksonModuleScala = "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.5"
  val playJson = "com.typesafe.play" %% "play-json" % "2.6.10"

//Notification
  val pushoverClient = "com.github.sps.pushover.net" % "pushover-client" % "1.0.0"

  //Sentiment
  val stanfordCoreNlp = "edu.stanford.nlp" % "stanford-corenlp" % "3.9.1"
  val stanfordCoreNlpClassifier = "edu.stanford.nlp" % "stanford-corenlp" % "3.9.1" classifier "models"

//Html
  val htmlCleaner = "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.6.1"
  val scalaScraper = "net.ruippeixotog" %% "scala-scraper" % "2.0.0"
  val htmlParser = "org.htmlparser" % "htmlparser" % "2.1"

  //Pdf
  val pdfbox = "org.apache.pdfbox" % "pdfbox" % "2.0.8"
  val pdfboxTools = "org.apache.pdfbox" % "pdfbox-tools" % "2.0.8"

}

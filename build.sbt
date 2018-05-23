import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.SbtNativePackager.autoImport.packageSummary

import com.typesafe.sbt.packager.linux.LinuxPlugin.autoImport.defaultLinuxInstallLocation
import com.typesafe.sbt.packager.rpm.RpmPlugin.autoImport.rpmVendor
import sbt.Keys.scalaVersion

//Versions
val sparkVersion = "2.2.0"
val globalVersion = "18.02.26.2"
val circeVersion = "0.9.3"
val paradiseVersion = "2.1.0"

//Organization
organization in ThisBuild := "TayloredDevelopment"
name in ThisBuild := "Trading_Scala"
version := globalVersion
version in ThisBuild := globalVersion
scalaVersion in ThisBuild := "2.12.4"
val globalBuildInfoKeys =
  Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
val globalBuildInfoPackage = "fraxses.gateway.version"
addCompilerPlugin(
  "org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
//Lagom Options
lagomServiceLocatorEnabled in ThisBuild := false
lagomServicesPortRange in ThisBuild := PortRange(60005, 60015)

//SBT Build Options
updateOptions := updateOptions.value.withCachedResolution(true)
logLevel := Level.Debug
fork := true
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  // "-Xfatal-warnings",
  "-language:reflectiveCalls",
  "-target:jvm-1.8",
  "-Xlint" // Scala 2.11.x only
)
javaOptions in ThisBuild ++= Seq(
  "-Xmx4096M",
  "-Xss2m",
  "-XX:MaxPermSize=2048M",
  "-XX:ReservedCodeCacheSize=2G",
  "-XX:+TieredCompilation",
  "-XX:+CMSPermGenSweepingEnabled",
  "-XX:+CMSClassUnloadingEnabled",
  "-XX:+UseConcMarkSweepGC",
  "-XX:+HeapDumpOnOutOfMemoryError"
)

//Additional Dependency Resolvers
resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

//Conf Settings

lazy val setConfiguration = {
  mappings in Universal += {
    val conf = (resourceDirectory in Compile).value / "application.conf"
    conf -> "conf/prod.conf"
  }
}

lazy val setDummyConfiguration = {
  mappings in Universal += {
    val conf = (resourceDirectory in Compile).value / "application.conf"
    conf -> "bin/conf/ignore.me"
  }

}

//Packaging Settings
enablePlugins(JavaAppPackaging)
enablePlugins(ClasspathJarPlugin)
enablePlugins(UniversalPlugin)
enablePlugins(LinuxPlugin)
enablePlugins(RpmPlugin)
enablePlugins(DebianPlugin)
enablePlugins(WindowsPlugin)

lazy val setBashParams = {
  bashScriptExtraDefines ++= Seq(
    """addJava "-Xmx4g"""",
    """addJava "-Xss2M"""",
    """addJava "-XX:MaxPermSize=2G"""",
    """addJava "-XX:ReservedCodeCacheSize=2G"""",
    """addJava "-XX:+CMSClassUnloadingEnabled"""",
    """addJava "-Dconfig.file=${app_home}/../conf/prod.conf""""
  )
}
lazy val setBatParams = {
  batScriptExtraDefines += """set _JAVA_OPTS=%_JAVA_OPTS% -Dconfig.file=%~dp0\\..\\conf\\prod.conf"""
}

//Core Libraries
lazy val commonSettings = Seq(
  libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.5",
  libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided",
  libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1",
  libraryDependencies += "co.fs2" %% "fs2-core" % "0.10.1",
  libraryDependencies += "co.fs2" %% "fs2-io" % "0.10.1",
  libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3",
  libraryDependencies += "org.typelevel" %% "cats-effect" % "1.0.0-RC",
  libraryDependencies += "io.bfil" %% "rx-kafka-core" % "0.2.0",
  libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "0.13.0",
  libraryDependencies += "org.reactivemongo" %% "reactivemongo-akkastream" % "0.13.0",
  libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.12",
  libraryDependencies += "com.msilb" %% "scalanda-v20" % "0.1.4",
  libraryDependencies += "io.circe" %% "circe-core" % circeVersion,
  libraryDependencies += "io.circe" %% "circe-generic" % circeVersion,
  libraryDependencies += "io.circe" %% "circe-parser" % circeVersion,
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0",
  libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.5",
  libraryDependencies +=  "com.danielasfregola" %% "twitter4s" % "5.5"
)

lazy val packageSettings = Seq(
  //Linux General Settings
  maintainer := "Jason Ridgway-Taylor <jason@tayloreddevelopment.co.uk>",
  packageSummary := "Trading_Scala",
  packageDescription := "Scala flavour of Trading app",
  defaultLinuxInstallLocation := "/opt/tayloreddevelopment",
  defaultLinuxLogsLocation := defaultLinuxInstallLocation + "/" + name,
  linuxPackageSymlinks := Seq.empty,
  rpmRelease := "1",
  rpmVendor := "tayloreddevelopment.co.uk",
  rpmUrl := Some("http://www.tayloreddevelopment.co.uk/"),
  rpmLicense := Some("Commercial"),
  rpmPrefix := Some("/opt/tayloreddevelopment"),
  wixProductId := "EF5F0916-2ED5-478D-AE9A-22DF5633E7A5",
  wixProductUpgradeId := "A20A2F6D-A5F3-4FB8-837A-CBEE192DA960"
)

lazy val sentimentSettings = Seq(
  libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0",
  libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0" classifier "models"
)

lazy val htmlSettings = Seq(
  libraryDependencies += "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.6.1",
  libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
  libraryDependencies += "org.htmlparser" % "htmlparser" % "2.1"
)

lazy val pdfSettings = Seq(
  libraryDependencies += "org.apache.pdfbox" % "pdfbox" % "2.0.8",
  libraryDependencies += "org.apache.pdfbox" % "pdfbox-tools" % "2.0.8"
)

lazy val notificationSettings = Seq(
  libraryDependencies += "com.github.sps.pushover.net" % "pushover-client" % "1.0.0"
)

lazy val versioning =
  (project in file("versioning"))
    .enablePlugins(BuildInfoPlugin, LagomScala)
    .settings(
      version := globalVersion,
      buildInfoKeys := globalBuildInfoKeys,
      buildInfoPackage := globalBuildInfoPackage
    )

lazy val core =
  (project in file("core"))
    .dependsOn(versioning)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "core",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(notificationSettings: _*)
    .settings(htmlSettings: _*)
    .settings(pdfSettings: _*)
    .settings(sentimentSettings: _*)
    .settings(commonSettings: _*)

lazy val news =
  (project in file("news"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "news",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(notificationSettings: _*)
    .settings(htmlSettings: _*)
    .settings(pdfSettings: _*)
    .settings(sentimentSettings: _*)
    .settings(commonSettings: _*)

lazy val pricing =
  (project in file("pricing"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "pricing",
      version := globalVersion,
      setConfiguration
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val valuation =
  (project in file("valuation"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "valuation",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val strategy =
  (project in file("strategy"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "strategy",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val order =
  (project in file("order"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "order",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val deal =
  (project in file("deal"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "deal",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val evaluation =
  (project in file("evaluation"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "evaluation",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val backtest =
  (project in file("backtest"))
    .dependsOn(core)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "backtest",
      version := globalVersion
    )
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val tradingApi =
  (project in file("tradingApi"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
      libraryDependencies += lagomScaladslApi,
      libraryDependencies += lagomJavadslKafkaBroker,
      libraryDependencies += lagomJavadslPersistenceCassandra
    )
    .settings(commonSettings: _*)

lazy val trading =
  (project in file("trading"))
    .enablePlugins(LagomScala)
    .settings(
      packageName := "trading",
      version := globalVersion,
      lagomServicePort := 60005,
      libraryDependencies += lagomJavadslKafkaBroker,
      libraryDependencies += lagomJavadslPersistenceCassandra
    )
    .settings(commonSettings: _*)
    .settings(setBashParams: _*)
    .settings(setBatParams: _*)
    .settings(packageSettings: _*)
    .dependsOn(core, tradingApi)

//lazy val root = (project in file("."))
//  .settings(name := "trading_scala")
//  .aggregate(versioning, core, tradingApi, trading, pricing)
//  .settings(commonSettings: _*)

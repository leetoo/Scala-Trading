import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.SbtNativePackager.autoImport.packageSummary

import com.typesafe.sbt.packager.linux.LinuxPlugin.autoImport.defaultLinuxInstallLocation
import com.typesafe.sbt.packager.rpm.RpmPlugin.autoImport.rpmVendor
import sbt.Keys.scalaVersion

//Versions
val sparkVersion = "2.2.0"
val globalVersion = "18.02.26.2"

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
  "-Xmx8192M",
  "-Xss2m",
  "-XX:MaxPermSize=4096M",
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


//Core Libraries
lazy val commonSettings = Seq(
  libraryDependencies ++=  Seq(
    Dependencies.jacksonModuleScala,
    Dependencies.playJson,
    Dependencies.macros,
    Dependencies.catsCore,
    Dependencies.fs2Core,
    Dependencies.fs2Io,
    Dependencies.shapeless,
    Dependencies.fs2Core,
    Dependencies.fs2Io,
    Dependencies.shapeless,
    Dependencies.catsEffect,
    Dependencies.rxKafkaCore,
    Dependencies.akkaStream,
    Dependencies.circeCore,
    Dependencies.circeGeneric,
    Dependencies.circeParser,
    Dependencies.scalactic,
    Dependencies.scalaTest,
    Dependencies.rxScala,
    Dependencies.rome,
    Dependencies.twitter4s,
    Dependencies.catsFree,
    Dependencies.monix
  )
)


lazy val sentimentSettings =Seq(
  libraryDependencies ++=  Seq(
    Dependencies.stanfordCoreNlp,
    Dependencies.stanfordCoreNlpClassifier
  )
)
lazy val htmlSettings = Seq(
  libraryDependencies ++=  Seq(
    Dependencies.htmlCleaner,
    Dependencies.scalaScraper,
    Dependencies.htmlParser
  )
)

lazy val pdfSettings = Seq(
  libraryDependencies ++=  Seq(
    Dependencies.pdfbox,
    Dependencies.pdfboxTools
  )
)

lazy val notificationSettings = Seq(
  libraryDependencies ++=  Seq(
    Dependencies.pushoverClient
  )
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

    .settings(
      packageName := "core",
      version := globalVersion
    )
    .settings(notificationSettings: _*)
    .settings(htmlSettings: _*)
    .settings(pdfSettings: _*)
    .settings(sentimentSettings: _*)
    .settings(commonSettings: _*)

lazy val domain =
  (project in file("domain"))
    .dependsOn(versioning,core)

    .settings(
      packageName := "domain",
      version := globalVersion
    )

    .settings(notificationSettings: _*)
    .settings(htmlSettings: _*)
    .settings(pdfSettings: _*)
    .settings(sentimentSettings: _*)
    .settings(commonSettings: _*)


lazy val persistence =
  (project in file("persistence"))
    .dependsOn(versioning,core,domain)

    .settings(
      packageName := "persistence",
      version := globalVersion
    )

    .settings(notificationSettings: _*)
    .settings(htmlSettings: _*)
    .settings(pdfSettings: _*)
    .settings(sentimentSettings: _*)
    .settings(commonSettings: _*)

lazy val providers =
  (project in file("providers"))
    .dependsOn(versioning,core,domain)

    .settings(
      packageName := "providers",
      version := globalVersion
    )

    .settings(notificationSettings: _*)
    .settings(htmlSettings: _*)
    .settings(pdfSettings: _*)
    .settings(sentimentSettings: _*)
    .settings(commonSettings: _*)


lazy val news =
  (project in file("news"))
    .dependsOn(core,versioning)
    .settings(
      packageName := "news",
      version := globalVersion
    )

    .settings(notificationSettings: _*)
    .settings(htmlSettings: _*)
    .settings(pdfSettings: _*)
    .settings(sentimentSettings: _*)
    .settings(commonSettings: _*)

lazy val pricing =
  (project in file("pricing"))
    .dependsOn(core,versioning,domain,persistence,providers)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "pricing",
      version := globalVersion,
      setConfiguration
    )

    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val valuation =
  (project in file("valuation"))
    .dependsOn(core,versioning,domain,persistence,providers)
    .settings(
      packageName := "valuation",
      version := globalVersion
    )

    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val strategy =
  (project in file("strategy"))
    .dependsOn(core,versioning,domain,persistence,providers)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "strategy",
      version := globalVersion
    )

    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val order =
  (project in file("order"))
    .dependsOn(core,versioning,domain,persistence,providers)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "order",
      version := globalVersion
    )

    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val deal =
  (project in file("deal"))
    .dependsOn(core,versioning,domain,persistence,providers)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "deal",
      version := globalVersion
    )

    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val evaluation =
  (project in file("evaluation"))
    .dependsOn(core,versioning,domain,persistence,providers)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "evaluation",
      version := globalVersion
    )

    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val backtest =
  (project in file("backtest"))
    .dependsOn(core,versioning,domain,persistence,providers)
    .enablePlugins(LagomScala)
    .settings(
      packageName := "backtest",
      version := globalVersion
    )

    .settings(packageSettings: _*)
    .settings(commonSettings: _*)

lazy val tradingApi =
  (project in file("tradingApi"))
    .dependsOn(core,versioning,domain,persistence,providers)
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
    .dependsOn(core,versioning,domain,persistence,providers,tradingApi)
    .settings(
      packageName := "trading",
      version := globalVersion,
      lagomServicePort := 60005,
      libraryDependencies += lagomJavadslKafkaBroker,
      libraryDependencies += lagomJavadslPersistenceCassandra
    )
    .settings(commonSettings: _*)

    .settings(packageSettings: _*)



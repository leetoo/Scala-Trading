import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.SbtNativePackager.autoImport.packageSummary

import com.typesafe.sbt.packager.linux.LinuxPlugin.autoImport.defaultLinuxInstallLocation
import com.typesafe.sbt.packager.rpm.RpmPlugin.autoImport.rpmVendor
import sbt.Keys.scalaVersion

//Versions
val sparkVersion = "2.2.0"
val globalVersion = "18.02.26.2"
val circeVersion = "0.9.3"



//Organization
organization in ThisBuild := "TayloredDevelopment"
name in ThisBuild := "Trading_Scala"
version := globalVersion
version in ThisBuild := globalVersion
scalaVersion in ThisBuild := "2.12.4"
val globalBuildInfoKeys =
  Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
val globalBuildInfoPackage = "fraxses.gateway.version"

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
  libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided",
  libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1",
  libraryDependencies += "co.fs2" %% "fs2-core" % "0.10.1",
  libraryDependencies += "co.fs2" %% "fs2-io" % "0.10.1",
  libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3",
  libraryDependencies += "io.monix" %% "monix" % "3.0.0-RC1",
  libraryDependencies += "org.typelevel" %% "cats-effect" % "1.0.0-RC",
  libraryDependencies +=  "io.bfil" %% "rx-kafka-core" % "0.2.0",
  libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "0.13.0",
  libraryDependencies += "com.msilb" %% "scalanda-v20" % "0.1.4",
  libraryDependencies += "io.circe" %% "circe-core" % circeVersion,
  libraryDependencies += "io.circe" %% "circe-generic" % circeVersion,
  libraryDependencies += "io.circe" %% "circe-parser" % circeVersion

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

lazy val versioning =
  (project in file("versioning"))
    .enablePlugins(BuildInfoPlugin)
    .settings(
      version := globalVersion,
      buildInfoKeys := globalBuildInfoKeys,
      buildInfoPackage := globalBuildInfoPackage
    )

lazy val core =
  (project in file("core"))
    .dependsOn(versioning)
    .settings(
      version := globalVersion,
    )
    .settings(commonSettings: _*)

lazy val pricing =
  (project in file("pricing"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
    )
    .settings(commonSettings: _*)

lazy val valuation =
  (project in file("valuation"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
    )
    .settings(commonSettings: _*)

lazy val strategy =
  (project in file("strategy"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
    )
    .settings(commonSettings: _*)

lazy val order =
  (project in file("order"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
    )
    .settings(commonSettings: _*)
	
lazy val deal =
  (project in file("deal"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
    )
    .settings(commonSettings: _*)
	
lazy val evaluation =
  (project in file("evaluation"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
    )
    .settings(commonSettings: _*)

lazy val backtest =
  (project in file("backtest"))
    .dependsOn(core)
    .settings(
      version := globalVersion,
    )
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
    .enablePlugins(LagomScala,
                   ClasspathJarPlugin,
                   LinuxPlugin,
                   RpmPlugin,
                   DebianPlugin,
                   WindowsPlugin)
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

lazy val root = (project in file("."))
  .settings(name := "trading_scala")
  .aggregate(versioning, core, tradingApi, trading)
  .settings(commonSettings: _*)
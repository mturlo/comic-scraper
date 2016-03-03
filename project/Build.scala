import play.sbt.routes.RoutesKeys._
import sbt.Keys._
import sbt._

object ComicScraperBuild extends Build {

  object V {
    val play = "2.4.6"
  }

  val appName = "comic-scraper"

  val appVersion = "0.0.1-SNAPSHOT"

  val appResolvers = Seq(
    Resolver.mavenLocal
  )

  val appDependencies = Seq(
    "net.ruippeixotog" %% "scala-scraper" % "0.1.2"
  )

  val applicationSettings = Seq(
    version := appVersion,
    libraryDependencies ++= appDependencies,
    scalaVersion := "2.11.7",
    resolvers ++= appResolvers,
    scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions", "-language:postfixOps"),
    fork in test := true,
    javaOptions in test += "-Dconfig.file=test/resources/application.conf -XX:MaxPermSize=512M " +
      "-Xmx1024M -Xms1024M -Duser.timezone=UTC -Djava.library.path=/usr/local/lib",
    resourceDirectory in Test <<= baseDirectory apply { (baseDir: File) => baseDir / "test" / "resources" }
  )

  val main = Project(appName, file("."))
    .enablePlugins(play.sbt.PlayScala)
    .settings(applicationSettings: _*)

}

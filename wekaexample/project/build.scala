import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.earldouglas.xwp.JettyPlugin
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object WekaexampleBuild extends Build {
  val Organization = "com.ajug.demo"
  val Name = "WekaExample"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.6"
  val ScalatraVersion = "2.4.1"

  lazy val project = Project (
    "wekaexample",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "nz.ac.waikato.cms.weka" % "weka-stable" % "3.6.13",
        "org.scalatest" %% "scalatest" % "2.2.6" % "test",
        "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container;compile",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  ).enablePlugins(JettyPlugin)
}

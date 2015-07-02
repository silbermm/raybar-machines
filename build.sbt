name := """raybar-machines"""

versionWithGit

scalariformSettings

lazy val root = (project in file(".")).enablePlugins(PlayScala)

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;controllers.javascript;controllers.ref;securesocial.*;views.*;controllers.*;"

(testOptions in Test) += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/report")

scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Ywarn-dead-code",
  "-Ywarn-unused-import",
  "-encoding", "UTF-8"
)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
 "org.webjars" % "bootstrap" % "3.3.4", 
  specs2 % Test,
  "org.scalatestplus" %% "play" % "1.1.0" % Test,
  "org.scalacheck" %% "scalacheck" % "1.11.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

pipelineStages := Seq(uglify, digest)

routesGenerator := InjectedRoutesGenerator

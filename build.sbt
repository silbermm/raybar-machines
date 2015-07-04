import sbt.Project.projectToRef

lazy val clients = Seq(raybarMachinesClient)
lazy val scalaV = "2.11.6"

scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Ywarn-dead-code",
  "-Ywarn-unused-import",
  "-encoding", "UTF-8"
)

lazy val raybarServer = (project in file("raybar-machines-server")).settings(
  name := """raybar-machines""",
  scalaVersion := scalaV,
  scalaJSProjects := clients,
  pipelineStages := Seq(scalaJSProd, gzip),
  resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  libraryDependencies ++= Seq(
    "com.vmunier" %% "play-scalajs-scripts" % "0.3.0",
    cache,
    specs2 % Test
  ),
  routesGenerator := InjectedRoutesGenerator,
  // Heroku specific
  herokuAppName in Compile := "raybar-machines",
  herokuSkipSubProjects in Compile := false
).enablePlugins(PlayScala).
  aggregate(clients.map(projectToRef): _*).
  dependsOn(exampleSharedJvm)

lazy val raybarMachinesClient = (project in file("raybar-machines-client")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  sourceMapsDirectories += exampleSharedJs.base / "..",
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0",
    "com.lihaoyi" %%% "utest" % "0.3.0" % "test" 
  ),
  //jsDependencies += RuntimeDOM,
  scalaJSStage in Global := FastOptStage,
  testFrameworks += new TestFramework("utest.runner.Framework")
).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(exampleSharedJs)

lazy val raybarShared = (crossProject.crossType(CrossType.Pure) in file("raybar-machines-shared")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSPlay).
  jsSettings(sourceMapsBase := baseDirectory.value / "..")

lazy val exampleSharedJvm = raybarShared.jvm
lazy val exampleSharedJs = raybarShared.js

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project raybarServer", _: State)) compose (onLoad in Global).value


import sbt.Keys._

organization := "gael.renoux"
name := "marwoge"
version := "0.1.0"

/* Allows to overwrite with publishLocal */
isSnapshot := true

scalaVersion := "3.9.0"

scalacOptions ++= Seq(
  "-no-indent", // Remove the significant-indentation syntax

  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-Werror", // Fail the compilation if there are any warnings.
  "-explain", // Explain type errors in more detail.

  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.

  "-Wsafe-init", // Wrap field accessors to throw an exception on uninitialized access.

  "-Wunused:explicits", // Warn if an explicit parameter is unused.
  "-Wunused:implicits", // Warn if an implicit parameter is unused.
  "-Wunused:imports", // Warn when imports are unused.
  "-Wunused:locals", // Warn if a local definition is unused.
  // "-Wunused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Wunused:privates", // Warn if a private member is unused.
  "-Wvalue-discard" // Warn when non-Unit expression results are unused.

  // "-XX:MaxInlineLevel=18", // see https://github.com/scala/bug/issues/11627#issuecomment-514619316 // check if still valid for Scala 3
)

libraryDependencies ++= Seq(
  
  "com.github.scopt" %% "scopt" % "4.2.0",

  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "ch.qos.logback" % "logback-classic" % "1.6.5",

  /* Tests */
  "com.github.jatcwang" %% "difflicious-scalatest" % "0.7.1" % Test,
  "org.scalatest" %% "scalatest" % "3.2.20" % Test
)


assembly / mainClass := Some("fr.renoux.marwoge.command.Main")
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

Test / fork := true
Test / testOptions += Tests.Argument("-oD") // show test duration

/* Stays inside the sbt console when we press "ctrl-c" in tests" */
Global / cancelable := true

/* Exposes the project's version to the code (displayed by the command line) */
Compile / sourceGenerators += Def.task {
  val file = (Compile / sourceManaged).value / "fr" / "renoux" / "marwoge" / "BuildInfo.scala"
  IO.write(file,
    s"""package fr.renoux.gaston
       |
       |object BuildInfo {
       |  val Version: String = "${version.value}"
       |}
       |""".stripMargin)
  Seq(file)
}.taskValue

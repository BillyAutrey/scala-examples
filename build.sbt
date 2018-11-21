name := "scala-executable-example"

version := "0.1"

scalaVersion := "2.12.7"

//project/assembly.sbt has assembly included as an SBT plugin.
//See https://github.com/sbt/sbt-assembly for details

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.18"
)

//assembly stuff, builds an actual runnable executable instead of just the jar
//commenting this out will build a fatjar in /target/scala-2.12/
import sbtassembly.AssemblyPlugin.defaultUniversalScript
assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(defaultUniversalScript(shebang = false)))
assemblyJarName in assembly := s"${name.value}-${version.value}"
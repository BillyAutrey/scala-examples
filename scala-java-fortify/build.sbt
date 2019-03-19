import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

import scala.sys.process._

credentials += Credentials(
  Path.userHome / ".lightbend" / "commercial.credentials")
resolvers += "lightbend-commercial-releases" at
  "https://repo.lightbend.com/commercial-releases/"

// Task definitions
val translate: TaskKey[Unit] = taskKey("Fortify Translation")
val scan: TaskKey[Unit] = taskKey("Fortify Scan")
val cleanFortify: TaskKey[Unit] = taskKey("Fortify Clean")

def javaSources(base: File): String = ((base / "src") ** "*.java").getPaths().mkString(" ")
def formatClasspath(classPath: Keys.Classpath): String = classPath.map(_.data.getPath()).mkString(":")


val fpr = "scala-java-fortify.fpr"
val buildId = "scala-java-fortify"

// `scan` task
scan := {
  Seq("bash", "-c", s"rm -rf ${fpr}").!
  Seq("bash", "-c", s"sourceanalyzer -b $buildId -f ${fpr} -scan").!
}

// `cleanFortify` task
cleanFortify :=  {
  clean.value
  Seq("bash", "-c", s"sourceanalyzer -b $buildId -clean").!
}

translate := {
  streams.value.log.info(s"Running Scala translation")
  Def.sequential(
    Def.task { Seq("bash", "-c", s"sourceanalyzer -b $buildId -clean").! },
    clean in Compile,
    compile in Compile,
    Def.taskDyn {
      val javaSourceList: String  = javaSources(baseDirectory.value)
      val classpath: String = formatClasspath((fullClasspathAsJars in Compile).value)

      streams.value.log.info(s"Running Java translation on $javaSourceList")
      Def.task {
        Seq("bash", "-c", s"sourceanalyzer -b $buildId -cp $classpath $javaSourceList").!
      }
    }
  ).value
}

addCompilerPlugin(
  "com.lightbend" % "scala-fortify" % "1.0.13"
    classifier "assembly"
    cross CrossVersion.patch)
scalacOptions += s"-P:fortify:build=$buildId"

libraryDependencies ++= Seq(
  scalaTest % Test,
  akka,
  akkaTest % Test,
  akkaStreamTest % Test
)

name := "scala-fortify"
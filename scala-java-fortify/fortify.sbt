import Dependencies._
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

lazy val fortifySettings = Seq(
  addCompilerPlugin(
    "com.lightbend" % "scala-fortify" % "1.0.13"
      classifier "assembly"
      cross CrossVersion.patch),
  scalacOptions += s"-P:fortify:build=$buildId"
)

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    scalaTest % Test,
    akka,
    akkaTest % Test,
    akkaStreamTest % Test
  )
)

//defining sources again, just so we can modify for fortify only
lazy val scalaFortify = (project in file("scala-fortify")).settings {
  commonSettings ++
  fortifySettings ++
  Seq(
    name := "scala-fortify",
    translate := {
      streams.value.log.info(s"Running Scala translation")
      Def.sequential(
        Def.task { Seq("bash", "-c", s"sourceanalyzer -b $buildId -clean").! },
        clean in Compile,
        compile in Compile
      ).value
    }
  )
}

lazy val javaFortify = (project in file("java-fortify"))
  .dependsOn(scalaFortify)
  .settings(
    commonSettings ++
    Seq(
      name := "java-fortify",
      translate := {
        Def.taskDyn {
          val javaSourceList: String  = javaSources(baseDirectory.value)
          val classpath: String = formatClasspath((fullClasspathAsJars in Compile).value)

          streams.value.log.info(s"Running Java translation")
          Def.task {
            Seq("bash", "-c", s"sourceanalyzer -b $buildId -cp $classpath $javaSourceList").!
          }
        }.value
      }
    )
  )
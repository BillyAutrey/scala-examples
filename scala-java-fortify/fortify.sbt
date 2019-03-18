import Dependencies._
import scala.sys.process._

credentials += Credentials(
  Path.userHome / ".lightbend" / "commercial.credentials")
resolvers += "lightbend-commercial-releases" at
  "https://repo.lightbend.com/commercial-releases/"

//// `translate` task
val translate: TaskKey[Unit] = taskKey("Fortify Translation")


logLevel in translate := Level.Debug

// `scan` task
val fpr = "scala-java-fortify.fpr"
val scan: TaskKey[Unit] = taskKey("Fortify Scan")
scan := {
  Seq("bash", "-c", s"rm -rf ${fpr}")
  Seq("bash", "-c", s"sourceanalyzer -b scala-java-fortify -f ${fpr} -scan")
}

lazy val fortifySettings = Seq(
  addCompilerPlugin(
    "com.lightbend" % "scala-fortify" % "1.0.13"
      classifier "assembly"
      cross CrossVersion.patch),
  scalacOptions += s"-P:fortify:build=scala-java-fortify"
)

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    scalaTest % Test,
    akka,
    akkaTest % Test,
    akkaStreamTest % Test
  )
)

def javaSources(base: File): PathFinder = (base / "src") ** "*.java"

val printClasspath = taskKey("Print classpath")

//defining sources again, just so we can modify for fortify only
lazy val scalaFortify = (project in file("scala-fortify")).settings {
  commonSettings ++
  fortifySettings ++
  Seq(
    name := "scala-fortify"
  )
}

lazy val javaFortify = (project in file("java-fortify"))
  .dependsOn(scalaFortify)
  .settings(
    commonSettings ++
    Seq(
      translate := {
        Def.taskDyn {
          // each sources definition is of type Seq[File],
          //   giving us a Seq[Seq[File]] that we then flatten to Seq[File]
          //        Def.task {
          //          Seq("bash", "-c", s"sourceanalyzer -b scala-java-fortify -clean")
          //        },
          //        clean in Compile,
          val javaSourceList: String  = javaSources(baseDirectory.value).getPaths().mkString(" ")
          val classpath: String = (fullClasspathAsJars in Compile).value.map(_.data.getPath()).mkString(":")
          streams.value.log.info(s"Running translation")
          Def.task {
            Seq("bash", "-c", s"sourceanalyzer -b scala-java-fortify -cp $classpath $javaSourceList").!
          }
        }.value
      },
      name := "java-fortify"
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
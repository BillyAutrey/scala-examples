import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    scalaTest % Test,
    akka,
    akkaTest % Test,
    akkaStreamTest % Test
  )
)

lazy val scalaFortify = (project in file("scala-fortify")).settings{
  commonSettings ++ 
  Seq(
    name := "scala-fortify"
  )
}

lazy val javaFortify = (project in file("java-fortify"))
  .dependsOn(scalaFortify)
  .settings{
    inThisBuild(
      commonSettings ++
      Seq(
        name := "java-fortify"
      )
    )
}
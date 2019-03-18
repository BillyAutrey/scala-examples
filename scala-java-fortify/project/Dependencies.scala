import sbt._

object Dependencies {

  lazy val akkaVersion = "2.5.21"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val akka = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  lazy val akkaTest = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  lazy val akkaStreamTest = "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion
}

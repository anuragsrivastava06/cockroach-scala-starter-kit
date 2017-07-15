name := """cockroach-akka-http-starter-kit"""

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.knoldus"

val akkaVersion = "10.0.7"
val hikariSlickVersion = "3.2.0"
val mockitoVersion = "1.10.19"
val postgresqlVersion = "9.4.1212"
val scalaTestVersion = "3.0.1"
val h2DBVersion = "1.4.193"
val sparyVersion = "1.3.3"
val scalaGuiceVersion = "4.1.0"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % hikariSlickVersion,
  "io.spray" %% "spray-json" % sparyVersion,
  "net.codingwell" %% "scala-guice" % scalaGuiceVersion,
  "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
  "org.postgresql" % "postgresql" % postgresqlVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11.2",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.11.2",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "com.h2database" % "h2" % "1.4.196" % "test",
  "org.mockito" % "mockito-all" % "2.0.2-beta" % "test"
)

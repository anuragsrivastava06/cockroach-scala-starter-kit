name := """cockroach-akka-http-starter-kit"""

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.knoldus"

val akkaVersion = "10.0.7"
val hikariSlickVersion = "3.2.0"

libraryDependencies ++= Seq(
  "com.h2database"          %   "h2"                                %   "1.4.196"             % "test",
  "com.typesafe.akka"       %%  "akka-http-core"                    %   akkaVersion,
  "com.typesafe.akka"       %%  "akka-http-spray-json-experimental" %   "2.4.11.2",
  "com.typesafe.akka"       %%  "akka-http-testkit"                 %   akkaVersion           % "test",
  "com.typesafe.slick"      %%  "slick"                             %   hikariSlickVersion,
  "com.typesafe.slick"      %%  "slick-hikaricp"                    %   hikariSlickVersion,
  "io.spray"                %%  "spray-json"                        %   "1.3.3",
  "net.codingwell"          %%  "scala-guice"                       %   "4.1.0",
  "org.mockito"             %   "mockito-all"                       %   "1.10.19"             % "test",
  "org.postgresql"          %   "postgresql"                        %   "9.4.1212",
  "org.scalatest"           %%  "scalatest"                         %   "3.0.1"               % "test"
)

name := """cockroach-akka-http-starter-kit"""

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.knoldus"

val akkaVersion = "10.0.7"
val hikariSlickVersion = "3.2.0"
val mockitoVersion = "1.10.19"
val postgreSqlVersion = "9.4.1212"
val scalaTestVersion = "3.0.1"
val h2DBVersion = "1.4.193"
val sprayVersion = "1.3.3"
val scalaGuiceVersion = "4.1.0"

libraryDependencies ++= Seq(
  "com.h2database"          %   "h2"                                %   "1.4.196"             % "test",
  "com.typesafe.akka"       %%  "akka-http-core"                    %   akkaVersion,
  "com.typesafe.akka"       %%  "akka-http-experimental"            %   "2.4.11.2",
  "com.typesafe.akka"       %%  "akka-http-spray-json-experimental" %   "2.4.11.2",
  "com.typesafe.akka"       %%  "akka-http-testkit"                 %   akkaVersion           % "test",
  "com.typesafe.slick"      %%  "slick"                             %   hikariSlickVersion,
  "com.typesafe.slick"      %%  "slick-hikaricp"                    %   hikariSlickVersion,
  "io.spray"                %%  "spray-json"                        %   sprayVersion,
  "net.codingwell"          %%  "scala-guice"                       %   scalaGuiceVersion,
  "org.mockito"             %   "mockito-all"                       %   "2.0.2-beta"          % "test",
  "org.postgresql"          %   "postgresql"                        %   postgreSqlVersion,
  "org.scalatest"           %%  "scalatest"                         %   scalaTestVersion      % "test"
)

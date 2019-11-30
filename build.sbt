name := "databaseProject"

version := "0.1"

scalaVersion := "2.13.1"

lazy val slickVersion = "3.3.2"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % "42.2.5",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.flywaydb" % "flyway-core" % "6.0.4",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

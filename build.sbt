name := "databaseProject"

version := "0.1"

scalaVersion := "2.13.1"

lazy val slickVersion = "3.3.2"

lazy val doobieVersion = "0.8.8"

libraryDependencies ++= Seq(

//  slick
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,

//  postgres
  "org.postgresql" % "postgresql" % "42.2.5",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",

//  loggers
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",

  //flyway
  "org.flywaydb" % "flyway-core" % "6.0.4",

  //doobie
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "org.tpolecat" %% "doobie-quill"    % doobieVersion,
)

name := "sql-typed"

version := "0.1-SNAPSHOT"

organization := "org.apache.spark.sql"

scalaVersion := "2.10.4"

libraryDependencies += "org.apache.spark" %% "spark-catalyst" % "1.2.0-SNAPSHOT"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.2.0-SNAPSHOT"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "ch.epfl.lamp" %% "scala-records" % "0.3"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)

initialCommands in console := """
  import org.apache.spark.sql.test.TestSQLContext._
  import org.apache.spark.sql.TypedSQL._
  org.apache.spark.sql.SQLMacros.currentContext = org.apache.spark.sql.test.TestSQLContext
  """

fork := true
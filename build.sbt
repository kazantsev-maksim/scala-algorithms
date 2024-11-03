import Dependencies.*

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .aggregate(algorithms, dataStructures, benchmarks)
  .settings(
    name := "scala-algorithms"
  )

lazy val algorithms = (project in file("algorithms"))
  .settings(name := "algorithms")

lazy val dataStructures = (project in file("data-structures"))
  .settings(name := "data-structures")

lazy val benchmarks = (project in file("benchmarks"))
  .settings(name := "benchmarks", libraryDependencies ++= Dependencies.Benchmarks.all)
  .dependsOn(algorithms, dataStructures)

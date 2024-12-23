ThisBuild / organization := "org.coinductive"

val Scala2 = "2.13.15"

ThisBuild / crossScalaVersions := Seq(Scala2, "3.6.1")
ThisBuild / scalaVersion := Scala2

ThisBuild / testFrameworks += new TestFramework("munit.Framework")

val catsV = "2.9.0"
val catsEffectV = "3.4.8"
val circeV = "0.14.5"
val munitCatsEffectV = "1.0.7"

lazy val core = (project in file("core"))
  .settings(
    name := "yfinance4s",

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % catsV,
      "org.typelevel" %% "cats-effect" % catsEffectV,

      "io.circe" %% "circe-core" % circeV,
      "io.circe" %% "circe-generic" % circeV,
      "io.circe" %% "circe-parser" % circeV,

      "org.typelevel" %% "munit-cats-effect-3" % munitCatsEffectV % Test,
    )
  )

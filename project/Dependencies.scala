import sbt._

object Dependencies {

  val test = Seq(
    "org.scalatest" %% "scalatest" % "3.0.4" % "test"
  )

  val scalafx = Seq(
    "org.scalafx" %% "scalafx" % "8.0.144-R12"
  )

  val deps = test ++ scalafx
}

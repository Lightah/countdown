name := """countdown"""

Test / fork := true

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(scalaVersion := "2.12.5")
  .settings(
    libraryDependencies ++= Dependencies.deps
  )

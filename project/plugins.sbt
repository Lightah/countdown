addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.3")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")

//Workaround for: [error] java.lang.NoClassDefFoundError: org/vafer/jdeb/Console
classpathTypes += "maven-plugin"

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

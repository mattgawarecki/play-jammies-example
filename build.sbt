name := """play-java-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  // javaJdbc, // Play! Java database plugin
  // javaWs    // Play! Java web services client plugin
  "com.mattgawarecki" % "play-jammies" % "1.0.0"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

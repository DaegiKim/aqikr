name := """aqikr"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  guice,
  evolutions,
  jdbc,
  javaWs,
  "org.ocpsoft.prettytime" % "prettytime" % "3.2.5.Final",
  "org.apache.commons" % "commons-lang3" % "3.8.1",
  "com.h2database" % "h2" % "1.4.192",
  "org.glassfish.jaxb"% "jaxb-core"% "2.3.0.1",
  "org.glassfish.jaxb"% "jaxb-runtime"% "2.3.2"
)

includeFilter in (Assets, LessKeys.less) := "spectre.less"
includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"

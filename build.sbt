import AssemblyKeys._ // put this at the top of the file

assemblySettings

name := "cassy"

scalaVersion := "2.10.3"

version := "0.1"


libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-core" % "1.1.0").
    exclude("org.mortbay.jetty", "servlet-api").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("commons-collections", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog"),
    "com.datastax.spark" %% "spark-cassandra-connector" % "1.1.0-alpha4",
    "org.apache.spark" %% "spark-sql" % "1.1.0"
)

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}
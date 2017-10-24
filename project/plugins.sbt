logLevel := Level.Warn

resolvers ++= Seq(
  "artifactory-maven" at "http://artifactory.lolhens.de/artifactory/maven-public/",
  Resolver.url("artifactory-ivy", url("http://artifactory.lolhens.de/artifactory/ivy-public/"))(Resolver.ivyStylePatterns)
)

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.3")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.3")

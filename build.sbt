name := (name in ThisBuild).value

inThisBuild(Seq(
  name := "Terra-Infinita",
  organization := "com.dafttech",
  version := "0.1.0",

  scalaVersion := "2.12.4",

  resolvers ++= Seq(
    "artifactory-maven" at "http://artifactory.lolhens.de/artifactory/maven-public/",
    Resolver.url("artifactory-ivy", url("http://artifactory.lolhens.de/artifactory/ivy-public/"))(Resolver.ivyStylePatterns)
  ),

  scalacOptions ++= Seq("-Xmax-classfile-name", "127")
))

lazy val root = project.in(file("."))
  .settings(publishArtifact := false)
  .aggregate(
    terra
  )

lazy val engine = project
  .settings(
    libraryDependencies ++= Seq(
      //"com.typesafe.akka" % "akka-actor" %% "2.5.6",
      "com.badlogicgames.gdx" % "gdx" % "1.5.5",
      "com.badlogicgames.gdx" % "gdx-freetype" % "1.5.5",
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.5.5",
      "com.badlogicgames.gdx" % "gdx-platform" % "1.5.5" classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-freetype-platform" % "1.5.5" classifier "natives-desktop"
    )
  )

lazy val terra = project
  .settings(name := (name in ThisBuild).value)
  .settings(
    libraryDependencies ++= Seq(
      //"com.typesafe.akka" % "akka-actor" %% "2.5.6",
    )
  )
  .dependsOn(engine)

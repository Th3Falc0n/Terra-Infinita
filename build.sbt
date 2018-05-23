name := (name in ThisBuild).value

inThisBuild(Seq(
  name := "Terra-Infinita",
  organization := "com.dafttech",
  version := "0.1.0",

  scalaVersion := "2.12.6",

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
      "com.badlogicgames.gdx" % "gdx" % "1.5.5",
      "com.badlogicgames.gdx" % "gdx-freetype" % "1.5.5",
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.5.5",
      "com.badlogicgames.gdx" % "gdx-platform" % "1.5.5" classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-freetype-platform" % "1.5.5" classifier "natives-desktop",
      "org.typelevel" %% "cats-core" % "1.1.0",
      "io.monix" %% "monix" % "3.0.0-RC1"
    )
  )

lazy val terra = project
  .settings(name := (name in ThisBuild).value)
  .settings(
    libraryDependencies ++= Seq(),

    assemblyOption in assembly := {
      def universalScript(shellCommands: String,
                          cmdCommands: String,
                          shebang: Boolean): String = {
        Seq(
          if (shebang) "#!/usr/bin/env sh" else "",
          "@ 2>/dev/null # 2>nul & echo off & goto BOF\r",
          ":",
          shellCommands.replaceAll("\r\n|\n", "\n"),
          "exit",
          Seq(
            "",
            ":BOF",
            cmdCommands.replaceAll("\r\n|\n", "\r\n"),
            "exit /B %errorlevel%",
            ""
          ).mkString("\r\n")
        ).filterNot(_.isEmpty).mkString("\n")
      }

      def defaultUniversalScript(javaOpts: Seq[String] = Seq.empty,
                                 shebang: Boolean = true,
                                 console: Boolean = true): Seq[String] = {
        val javaCommand = s"${if (console) "java" else "javaw"} -jar"
        val javaOptsString = javaOpts.map(_ + " ").mkString
        Seq(universalScript(
          shellCommands = s"""exec $javaCommand $javaOptsString$$JAVA_OPTS "$$0" "$$@"""",
          cmdCommands = s"""$javaCommand $javaOptsString%JAVA_OPTS% "%~dpnx0" %*""",
          shebang = shebang
        ))
      }

      (assemblyOption in assembly).value.copy(prependShellScript = Some(defaultUniversalScript()))
    },

    assemblyJarName in assembly := s"${name.value}-${version.value}.sh.bat"
  )
  .dependsOn(engine)

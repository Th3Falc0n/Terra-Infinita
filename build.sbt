name := (name in ThisBuild).value

inThisBuild(Seq(
  name := "Terra-Infinita",
  organization := "com.dafttech",
  version := "0.1.0",

  scalaVersion := "2.13.1"
))

lazy val root = project.in(file("."))
  .settings(publishArtifact := false)
  .aggregate(
    terra
  )

lazy val engine = project
  .settings(
    // TODO: gdx 1.9.7: com.badlogic.gdx.utils.GdxRuntimeException: Float texture FrameBuffer Attachment not available on GLES 2.0
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx" % "1.9.6",
      "com.badlogicgames.gdx" % "gdx-freetype" % "1.9.6",
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.9.6",
      "com.badlogicgames.gdx" % "gdx-platform" % "1.9.6" classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-freetype-platform" % "1.9.6" classifier "natives-desktop",
      "org.typelevel" %% "cats-core" % "2.1.1",
      "io.monix" %% "monix" % "3.2.1"
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

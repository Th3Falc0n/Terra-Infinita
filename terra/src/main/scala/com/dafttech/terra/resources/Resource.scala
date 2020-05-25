package com.dafttech.terra.resources

import java.nio.file.{Files, Path}

import cats.effect.Blocker
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap
import com.badlogic.gdx.graphics.glutils.PixmapTextureData
import com.badlogic.gdx.graphics.{Pixmap, TextureData}
import fs2.Stream
import monix.eval.Task
import monix.execution.Scheduler

object Resource {
  private val defaultChunkSize = 50 * 1024

  private val resourceBlocker = Blocker.liftExecutionContext(Scheduler.io("resources"))

  def fromClasspath(name: String, checkExists: Boolean = true): Stream[Task, Byte] = {
    val normalizedName = "/" + name.dropWhile(_ == '/')
    if (checkExists) {
      require(getClass.getResource(normalizedName) != null, "Couldn't find resource on classpath: " + normalizedName)
    }
    fs2.io.readInputStream(
      Task(getClass.getResourceAsStream(normalizedName)),
      chunkSize = defaultChunkSize,
      blocker = resourceBlocker
    )
  }

  def fromPath(path: Path, checkExists: Boolean = true): Stream[Task, Byte] = {
    if (checkExists) {
      require(Files.exists(path), "Couldn't find Path: " + path.toString)
    }
    fs2.io.file.readAll[Task](
      path,
      chunkSize = defaultChunkSize,
      blocker = resourceBlocker
    )
  }

  def toPixmap(byteStream: Stream[Task, Byte]): Task[Pixmap] =
    byteStream.compile.to(Array).map { bytes =>
      new Pixmap(new Gdx2DPixmap(bytes, 0, bytes.length, 0))
    }

  def toTextureData(byteStream: Stream[Task, Byte], useMipMaps: Boolean = false): Task[TextureData] =
    toPixmap(byteStream).map { pixmap =>
      new PixmapTextureData(pixmap, null, useMipMaps, false)
    }
}

package com.dafttech.terra.resources

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.utils._
import monix.eval.Task
import monix.execution.atomic.Atomic

import scala.concurrent.duration._

class ImageLibrary {
  private val atomicLibrary: Atomic[Map[String, TextureRegion]] = Atomic(Map.empty[String, TextureRegion])

  def load(name: String, path: String): Task[TextureRegion] =
    RenderThread(Task {
      val texture = new TextureRegion(new Texture(path))
      texture.flip(false, true)
      atomicLibrary.transform(_ + (name -> texture))
      texture
    }).memoize

  def loadImage(name: String, path: String): Unit = {
    import monix.execution.Scheduler.Implicits.global
    load(name, path).runSyncUnsafe(5.seconds)
  }

  def loadImage(name: String, path: String, num: Int): Unit =
    for (i <- 0 to num)
      loadImage(name + i, path.substring(0, path.lastIndexOf(".")) + "_" + i + path.substring(path.lastIndexOf(".")))

  val errorImageTask: Task[TextureRegion] = Task.defer(getImage("error")).memoizeOnSuccess

  def getImage(name: String): Task[TextureRegion] =
    Task.defer(atomicLibrary.get.get(name).map(Task.now).getOrElse(errorImageTask)) //.memoizeOnSuccess

  def getImage(name: String, num: Int): Task[TextureRegion] =
    getImage(name + num)
}
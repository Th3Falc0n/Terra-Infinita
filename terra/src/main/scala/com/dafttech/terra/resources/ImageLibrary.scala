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
      val pixmap = {
        val textureRegion = new TextureRegion(new Texture(path))
        val textureData = textureRegion.getTexture.getTextureData
        if (!textureData.isPrepared) textureData.prepare()
        textureData.consumePixmap()
      }
      val textureRegion = new TextureRegion(new Texture(pixmap))
      textureRegion.flip(false, true)
      atomicLibrary.transform(_ + (name -> textureRegion))
      textureRegion
    }).memoize

  def loadImage(name: String, path: String): Unit = {
    import com.dafttech.terra.utils.RenderThread._
    load(name, path).runSyncUnsafe(5.seconds)
  }

  def loadImage(name: String, path: String, num: Int): Unit =
    for (i <- 0 to num)
      loadImage(name + i, path.substring(0, path.lastIndexOf(".")) + "_" + i + path.substring(path.lastIndexOf(".")))

  private def getImageOption(name: String): Option[TextureRegion] =
    atomicLibrary.get.get(name)

  lazy val errorImage: TextureRegion = getImageOption("error").getOrElse(throw new RuntimeException("Failed to load error image!"))

  def getImage(name: String): TextureRegion =
    getImageOption(name).getOrElse(errorImage)

  def getImage(name: String, num: Int): TextureRegion =
    getImage(name + num)

  def getImageTask(name: String): Task[TextureRegion] = Task.now(getImage(name))

  def getImageTask(name: String, num: Int): Task[TextureRegion] = Task.now(getImage(name, num))
}
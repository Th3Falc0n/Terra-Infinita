package com.dafttech.terra.resources

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import monix.eval.{MVar, Task}
import com.dafttech.terra.utils._

class ImageLibrary { // TODO
  private val libraryVar: MVar[Map[String, TextureRegion]] = MVar(Map.empty[String, TextureRegion]).getSyncUnsafe

  def load(name: String, path: String): Task[TextureRegion] =
    for {
      library <- libraryVar.take
      texture <- Task(new TextureRegion(new Texture(path)))
      _ = texture.flip(false, true)
      _ <- libraryVar.put(library + (name -> texture))
    } yield
      texture

  val errorImageTask: Task[TextureRegion] = get("error")

  def get(name: String): Task[TextureRegion] =
    for {
      library <- libraryVar.read
      texture <- library.get(name).map(Task.now).getOrElse(errorImageTask)
    } yield
      texture

  private var library = Map.empty[String, TextureRegion]

  def loadImage(name: String, path: String): Unit = {
    val textureRegion = new TextureRegion(new Texture(path))
    textureRegion.flip(false, true)
    library = library + (name -> textureRegion)
  }

  def loadImage(name: String, path: String, num: Int): Unit =
    for (i <- 0 to num)
      loadImage(name + i, path.substring(0, path.lastIndexOf(".")) + "_" + i + path.substring(path.lastIndexOf(".")))

  lazy val errorImage: TextureRegion = library("error")

  def getImage(name: String): TextureRegion =
    library.getOrElse(name, errorImage)

  def getImage(name: String, num: Int): TextureRegion =
    library.getOrElse(name + num, errorImage)
}
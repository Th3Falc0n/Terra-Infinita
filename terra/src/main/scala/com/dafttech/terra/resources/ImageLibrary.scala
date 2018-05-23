package com.dafttech.terra.resources

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class ImageLibrary {
  private var library: Map[String, TextureRegion] = Map.empty

  def loadImage(name: String, path: String) {
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
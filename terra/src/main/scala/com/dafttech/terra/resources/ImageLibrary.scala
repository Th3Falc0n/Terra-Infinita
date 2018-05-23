package com.dafttech.terra.resources

import java.util
import java.util.{HashMap, Map}

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class ImageLibrary {
  var library: util.Map[String, TextureRegion] = new util.HashMap[String, TextureRegion]

  def loadImage(name: String, path: String) {
    library.put(name, new TextureRegion(new Texture(path)))
    library.get(name).flip(false, true)
  }

  def loadImage(name: String, path: String, num: Int) {
    {
      var i: Int = 0
      for(i <- 0 to num) {
        library.put(name + i, new TextureRegion(new Texture(path.substring(0, path.lastIndexOf(".")) + "_" + i + path.substring(path.lastIndexOf(".")))))
        library.get(name + i).flip(false, true)
      }
    }
  }

  def getImage(name: String): TextureRegion = if (library.containsKey(name)) library.get(name) else library.get("error")
  def getImage(name: String, num: Int): TextureRegion = if (library.containsKey(name + num)) library.get(name + num) else library.get("error")
}
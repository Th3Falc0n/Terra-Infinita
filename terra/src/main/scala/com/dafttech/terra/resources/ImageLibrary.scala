package com.dafttech.terra.resources

import java.util.{HashMap, Map}

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class ImageLibrary {
  var library: Map[String, TextureRegion] = new HashMap[String, TextureRegion]

  def loadImage(name: String, path: String) {
    library.put(name, new TextureRegion(new Texture(path)))
    library.get(name).flip(false, true)
  }

  def loadImage(name: String, path: String, num: Int) {
    {
      var i: Int = 0
      while (i <= num) {
        {
          library.put(name + i, new TextureRegion(new Texture(path.substring(0, path.lastIndexOf(".")) + "_" + i + path.substring(path.lastIndexOf(".")))))
          library.get(name + i).flip(false, true)
        }
        ({
          i += 1;
          i - 1
        })
      }
    }
  }

  def getImage(name: String): TextureRegion = {
    return if (library.containsKey(name)) library.get(name) else library.get("error")
  }

  def getImage(name: String, num: Int): TextureRegion = {
    return if (library.containsKey(name + num)) library.get(name + num) else library.get("error")
  }
}
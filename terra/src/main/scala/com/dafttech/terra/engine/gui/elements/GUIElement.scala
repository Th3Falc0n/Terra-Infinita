package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.vector.Vector2d

abstract class GUIElement(p: Vector2d, s: Vector2d) extends GUIObject(p, s) {
  var image: TextureRegion = null

  def draw(screen: AbstractScreen): Unit = {
    val p: Vector2d = getScreenPosition
    screen.batch.begin()
    if (image != null)
      screen.batch.draw(image, p.x.toFloat, p.y.toFloat)
    else
      println(s"img null in $getClass base GUIElement")
    screen.batch.end()
    screen.batch.setColor(Color.WHITE)
  }
}
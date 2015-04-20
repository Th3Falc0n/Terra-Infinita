package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.{AbstractScreen, Vector2}

abstract class GUIElement(p: Vector2, s: Vector2) extends GUIObject(p, s) {
  var image: TextureRegion = null

  def draw(screen: AbstractScreen) {
    val p: Vector2 = getScreenPosition
    screen.batch.begin
    screen.batch.draw(image, p.x, p.y)
    screen.batch.end
    screen.batch.setColor(Color.WHITE)
  }
}
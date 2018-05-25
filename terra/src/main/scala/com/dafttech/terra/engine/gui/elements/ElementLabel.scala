package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.resources.Resources

class ElementLabel(p: Vector2, var text: String) extends GUIElement(p, null) {
  var clr: Color = Color.WHITE
  setText(text)

  def setText(txt: String): Unit = {
    text = txt
    val bnds: BitmapFont.TextBounds = Resources.GUI_FONT.getBounds(text)
    size = Vector2(bnds.width, bnds.height)
  }

  def setColor(c: Color): Unit = {
    clr = c
  }

  override def draw(screen: AbstractScreen): Unit = {
    val p: Vector2 = getScreenPosition
    screen.batch.begin
    Resources.GUI_FONT.setColor(clr)
    Resources.GUI_FONT.draw(screen.batch, text, p.x.toFloat, 6 + p.y.toFloat)
    screen.batch.end
  }
}
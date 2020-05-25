package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.renderer.TextBounds
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.resources.Resources

abstract class ElementButton(p: Vector2d, val text: String) extends GUIElement(p, Vector2d(100, 20)) {
  image = { // TODO: Scheduler
    Resources.GUI.getImage("button")
  }

  override def draw(screen: AbstractScreen): Unit = {
    val p: Vector2d = getScreenPosition
    if (mouseHover) {
      screen.batch.setColor(Color.GREEN)
      Resources.GUI_FONT.setColor(Color.GREEN)
    }
    else {
      screen.batch.setColor(Color.WHITE)
      Resources.GUI_FONT.setColor(Color.WHITE)
    }
    super.draw(screen)
    screen.batch.begin
    Resources.GUI_FONT.draw(screen.batch, text, p.x.toFloat + size.x.toFloat / 2 - TextBounds.getBounds(Resources.GUI_FONT, text).x.toFloat / 2, 6 + p.y.toFloat)
    screen.batch.end
    screen.batch.setColor(Color.WHITE)
  }

  final override def onClick(button: Int): Unit = {
    actionPerformed(button)
  }

  def actionPerformed(button: Int)
}
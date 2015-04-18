package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.resources.Resources

abstract class ElementButton extends GUIElement {
  private var text: CharSequence = "Button"

  def this(p: Vector2, txt: String) {
    this()
    `super`(p, new Vector2(100, 20))
    text = txt
    image = Resources.GUI.getImage("button")
    size = new Vector2(100, 20)
  }

  override def draw(screen: AbstractScreen) {
    val p: Vector2 = getScreenPosition
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
    Resources.GUI_FONT.draw(screen.batch, text, p.x + size.x / 2 - Resources.GUI_FONT.getBounds(text).width / 2, 6 + p.y)
    screen.batch.end
    screen.batch.setColor(Color.WHITE)
  }

  final override def onClick(button: Int) {
    actionPerformed(button)
  }

  def actionPerformed(button: Int)
}
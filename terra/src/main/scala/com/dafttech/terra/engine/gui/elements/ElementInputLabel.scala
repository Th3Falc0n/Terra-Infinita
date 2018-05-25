package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.dafttech.terra.engine.input.handlers.IStringInputHandler
import com.dafttech.terra.engine.input.{FocusManager, IFocusableTyping}
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.resources.Resources

class ElementInputLabel(p: Vector2, val handler: IStringInputHandler) extends GUIElement(p, Vector2.Null) with IFocusableTyping {
  private var text: String = ""
  private var renderText: String = ""
  var clr: Color = Color.WHITE
  private var time: Float = 0

  def setColor(c: Color) {
    clr = c
  }

  override def update(delta: Float): Unit = {
    super.update(delta)
    time += delta
    renderText = ">" + text + (if ((time % 1f) < 0.5 && FocusManager.hasTypeFocus(this)) "_" else "")
    val bnds: BitmapFont.TextBounds = Resources.GUI_FONT.getBounds(text)
    size = Vector2(bnds.width, bnds.height)
  }

  override def draw(screen: AbstractScreen): Unit = {
    val p: Vector2 = getScreenPosition
    screen.batch.begin
    Resources.GUI_FONT.setColor(clr)
    Resources.GUI_FONT.draw(screen.batch, renderText, p.x.toFloat, 6 + p.y.toFloat)
    screen.batch.end
  }

  def beginStringInput: Boolean = {
    return FocusManager.acquireTypeFocus(this)
  }

  def onKeyTyped(c: Char): Unit = {
    if (c == '\b' && text.length >= 1) {
      text = text.substring(0, text.length - 1)
    }
    if (!Character.isIdentifierIgnorable(c)) {
      text += c
    }
  }

  def onKeyDown(i: Int): Unit = {
    if (i == Keys.ENTER) {
      handler.handleInput(text)
      text = ""
      FocusManager.releaseTypeFocus(this)
    }
  }
}
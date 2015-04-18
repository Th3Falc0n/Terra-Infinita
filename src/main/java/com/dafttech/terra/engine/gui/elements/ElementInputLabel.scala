package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.engine.input.{FocusManager, IFocusableTyping}
import com.dafttech.terra.engine.input.handlers.IStringInputHandler
import com.dafttech.terra.resources.Resources

class ElementInputLabel extends GUIElement with IFocusableTyping {
  private var text: String = ""
  private var renderText: String = ""
  var clr: Color = Color.WHITE
  private[elements] var handler: IStringInputHandler = null
  private var time: Float = 0

  def this(p: Vector2, h: IStringInputHandler) {
    this()
    `super`(p, new Vector2)
    handler = h
  }

  def setColor(c: Color) {
    clr = c
  }

  override def update(delta: Float) {
    super.update(delta)
    time += delta
    renderText = ">" + text + (if ((time % 1f) < 0.5 && FocusManager.hasTypeFocus(this)) "_" else "")
    val bnds: BitmapFont.TextBounds = Resources.GUI_FONT.getBounds(text)
    size = new Vector2(bnds.width, bnds.height)
  }

  override def draw(screen: AbstractScreen) {
    val p: Vector2 = getScreenPosition
    screen.batch.begin
    Resources.GUI_FONT.setColor(clr)
    Resources.GUI_FONT.draw(screen.batch, renderText, p.x, 6 + p.y)
    screen.batch.end
  }

  def beginStringInput: Boolean = {
    return FocusManager.acquireTypeFocus(this)
  }

  def onKeyTyped(c: Char) {
    if (c == '\b' && text.length >= 1) {
      text = text.substring(0, text.length - 1)
    }
    if (!Character.isIdentifierIgnorable(c)) {
      text += c
    }
  }

  def onKeyDown(i: Int) {
    if (i == Keys.ENTER) {
      handler.handleInput(text)
      text = ""
      FocusManager.releaseTypeFocus(this)
    }
  }
}
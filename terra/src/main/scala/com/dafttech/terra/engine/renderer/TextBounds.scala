package com.dafttech.terra.engine.renderer

import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout}
import com.dafttech.terra.engine.vector.Vector2d

object TextBounds {
  private val layoutCache = new java.lang.ThreadLocal[GlyphLayout]() {
    override def initialValue(): GlyphLayout = new GlyphLayout()
  }

  def getBounds(font: BitmapFont, text: String): Vector2d = {
    val glyphLayout = layoutCache.get()
    glyphLayout.setText(font, text)
    Vector2d(glyphLayout.width, glyphLayout.height)
  }
}

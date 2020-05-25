package com.dafttech.terra.engine.renderer

import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout}
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.resources.Resources

object TextBounds {
  // TODO: cache GlyphLayout
  def getBounds(font: BitmapFont, text: String): Vector2d = {
    val glyphLayout = new GlyphLayout(font, text)
    Vector2d(glyphLayout.width, glyphLayout.height)
  }
}

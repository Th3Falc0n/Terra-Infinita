package com.badlogic.gdx.backends.lwjgl

import com.badlogic.gdx.backends.lwjgl.UnsafeLwjglGraphics._

class UnsafeLwjglGraphics(config: LwjglApplicationConfiguration) extends LwjglGraphics(config) {
  override def isGL30Available: Boolean = bufferHackEnabled || super.isGL30Available
}

object UnsafeLwjglGraphics {
  private var bufferHackEnabled = false

  def bufferHack(): Unit = bufferHackEnabled = true
}

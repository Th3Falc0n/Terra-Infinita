package com.badlogic.gdx.backends.lwjgl

import com.badlogic.gdx.backends.lwjgl.UnsafeLwjglGraphics._

class UnsafeLwjglGraphics(config: LwjglApplicationConfiguration) extends LwjglGraphics(config) {
  override def isGL30Available: Boolean =
    if (bufferHackEnabled) {
      bufferHackEnabled = false
      true
    } else
      super.isGL30Available
}

object UnsafeLwjglGraphics {
  private var bufferHackEnabled = false

  def bufferHack(): Unit = {
    if (bufferHackEnabled)
      throw new IllegalStateException("Buffer hack was already enabled!")
    bufferHackEnabled = true
  }
}

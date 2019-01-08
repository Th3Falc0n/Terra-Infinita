package com.dafttech.terra.engine.gui.containers

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.vector.Vector2d

class ContainerWindow(p: Vector2d, s: Vector2d) extends GUIContainer(p, s) {
  override def addObject(e: GUIObject): Unit = {
    if ((position rectangleTo size).contains(e.position rectangleTo e.size)) {
      super.addObject(e)
    }
    else {
      throw new IllegalArgumentException("Element is outside of window")
    }
  }
}
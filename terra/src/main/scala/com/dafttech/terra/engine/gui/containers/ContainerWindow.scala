package com.dafttech.terra.engine.gui.containers

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.GUIObject

class ContainerWindow(p: Vector2, s: Vector2) extends GUIContainer(p, s) {
  override def addObject(e: GUIObject) {
    if ((position rectangleTo size).contains(e.position rectangleTo e.size)) {
      super.addObject(e)
    }
    else {
      throw new IllegalArgumentException("Element is outside of window")
    }
  }
}
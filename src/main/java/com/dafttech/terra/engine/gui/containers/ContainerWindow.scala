package com.dafttech.terra.engine.gui.containers

import com.badlogic.gdx.math.Rectangle
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.GUIObject

class ContainerWindow extends GUIContainer {
  def this(p: Vector2, s: Vector2) {
    this()
    `super`(p, s)
  }

  override def addObject(e: GUIObject, index: Int) {
    if (new Rectangle(position.x, position.y, size.x, size.y).contains(new Rectangle(e.position.x, e.position.y, e.size.x, e.size.y))) {
      super.addObject(e, index)
    }
    else {
      throw new IllegalArgumentException("Element is outside of window")
    }
  }
}
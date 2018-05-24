package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

class AnchorBottom(val position: Float) extends GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer): Unit = {
    gObj.position = gObj.position.withY(container.size.y * (1f - position) - gObj.size.y)
  }
}
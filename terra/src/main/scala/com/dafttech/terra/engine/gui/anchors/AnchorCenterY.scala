package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

class AnchorCenterY extends GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer) {
    gObj.position = gObj.position.withY(container.size.y / 2 - gObj.size.y / 2)
  }
}
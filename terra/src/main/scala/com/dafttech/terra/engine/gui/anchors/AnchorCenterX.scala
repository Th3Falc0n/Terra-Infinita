package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

class AnchorCenterX extends GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer) {
    gObj.position = gObj.position.withX(container.size.x / 2 - gObj.size.x / 2)
  }
}
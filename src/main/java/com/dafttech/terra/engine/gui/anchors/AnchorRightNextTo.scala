package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

class AnchorRightNextTo(val distance: Float, val relTo: GUIObject) extends GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer) {
    gObj.position.x = relTo.position.x + relTo.size.x + distance
    gObj.position.y = relTo.position.y
  }
}
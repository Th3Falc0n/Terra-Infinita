package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

class AnchorRightNextTo(val relTo: GUIObject, val distance: Float) extends GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer) {
    gObj.position = Vector2(relTo.position.x + relTo.size.x + distance, relTo.position.y)
  }
}
package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer
import com.dafttech.terra.engine.vector.Vector2d

class AnchorRightNextTo(val relTo: GUIObject, val distance: Float) extends GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer): Unit = {
    gObj.position = Vector2d(relTo.position.x + relTo.size.x + distance, relTo.position.y)
  }
}
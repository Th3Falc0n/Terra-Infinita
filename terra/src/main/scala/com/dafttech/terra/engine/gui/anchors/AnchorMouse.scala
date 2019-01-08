package com.dafttech.terra.engine.gui.anchors

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer
import com.dafttech.terra.engine.vector.Vector2d

class AnchorMouse extends GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer): Unit = {
    gObj.position = Vector2d(Gdx.input.getX + 10, Gdx.input.getY - gObj.size.y - 10)
    if (gObj.position.x + gObj.size.x > Gdx.graphics.getWidth) {
      gObj.position = gObj.position.mapX(_ - 20 + gObj.size.x)
    }
    if (gObj.position.y < 0) {
      gObj.position = gObj.position.mapY(_ + gObj.size.y + 10)
    }
  }

  override def needsApplyOnFrame: Boolean = true

  override def isContainerDependent: Boolean = false
}
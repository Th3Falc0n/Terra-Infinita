package com.dafttech.terra.engine.gui

import com.dafttech.terra.engine.gui.anchors.{AnchorMouse, GUIAnchorSet}
import com.dafttech.terra.engine.gui.elements.{ElementLabel, GUIElement}
import com.dafttech.terra.engine.vector.Vector2d

object Tooltip {
  var label: ElementLabel = null

  def init: Unit = {
    label = new ElementLabel(Vector2d.Zero, "")
    val labelSet: GUIAnchorSet = new GUIAnchorSet
    labelSet.addAnchor(new AnchorMouse)
    label.assignAnchorSet(labelSet)
  }

  def getLabel: GUIElement = label

  def setText(txt: String): Unit = {
    label.setText(txt)
  }
}
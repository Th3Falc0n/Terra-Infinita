package com.dafttech.terra.engine.gui

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.anchors.{AnchorMouse, GUIAnchorSet}
import com.dafttech.terra.engine.gui.elements.{ElementLabel, GUIElement}

object Tooltip {
  var label: ElementLabel = null

  def init {
    label = new ElementLabel(Vector2.Null, "")
    val labelSet: GUIAnchorSet = new GUIAnchorSet
    labelSet.addAnchor(new AnchorMouse)
    label.assignAnchorSet(labelSet)
  }

  def getLabel: GUIElement = {
    return label
  }

  def setText(txt: String): Unit = {
    label.setText(txt)
  }
}
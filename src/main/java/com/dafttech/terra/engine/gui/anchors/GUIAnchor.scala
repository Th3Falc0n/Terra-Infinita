package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

abstract class GUIAnchor {
  def applyAnchor(gObj: GUIObject, container: GUIContainer)
  def needsApplyOnFrame: Boolean = false
  def isContainerDependent: Boolean = true
}
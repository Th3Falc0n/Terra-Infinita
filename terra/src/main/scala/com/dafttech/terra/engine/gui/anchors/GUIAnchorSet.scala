package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

import scala.collection.mutable;

class GUIAnchorSet {
  private val anchors: mutable.MutableList[GUIAnchor] = new mutable.MutableList[GUIAnchor];

  def this(can: GUIAnchor*) {
    this()
    anchors ++= can
  }

  def applyAnchorSet(gObj: GUIObject, container: GUIContainer) {
    for (a <- anchors) {
      a.applyAnchor(gObj, container)
    }
  }

  def needsApplyOnFrame: Boolean = {
    for (a <- anchors) {
      if (a.needsApplyOnFrame) return true
    }
    return false
  }

  def isContainerDependent: Boolean = {
    for (a <- anchors) {
      if (a.isContainerDependent) return true
    }
    return false
  }

  def addAnchor(anchor: GUIAnchor): GUIAnchorSet = {
    anchors += anchor
    return this
  }
}
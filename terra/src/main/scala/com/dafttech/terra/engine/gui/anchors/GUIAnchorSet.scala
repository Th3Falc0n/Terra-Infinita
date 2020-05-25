package com.dafttech.terra.engine.gui.anchors

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.gui.containers.GUIContainer

import scala.collection.mutable.ListBuffer

class GUIAnchorSet {
  private val anchors: ListBuffer[GUIAnchor] = new ListBuffer[GUIAnchor]

  def this(can: GUIAnchor*) {
    this()
    anchors ++= can
  }

  def applyAnchorSet(gObj: GUIObject, container: GUIContainer): Unit = {
    for (a <- anchors) {
      a.applyAnchor(gObj, container)
    }
  }

  def needsApplyOnFrame: Boolean = {
    for (a <- anchors) {
      if (a.needsApplyOnFrame) return true
    }
    false
  }

  def isContainerDependent: Boolean = {
    for (a <- anchors) {
      if (a.isContainerDependent) return true
    }
    false
  }

  def addAnchor(anchor: GUIAnchor): GUIAnchorSet = {
    anchors += anchor
    this
  }
}
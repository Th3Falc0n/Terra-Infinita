package com.dafttech.terra.engine.gui

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet
import com.dafttech.terra.engine.gui.containers.GUIContainer
import com.dafttech.terra.game.Events
import org.lolhens.eventmanager.EventListener

abstract class GUIObject {
  var position: Vector2 = null
  var size: Vector2 = null
  var mouseHover: Boolean = false
  protected var registeredEvents: Boolean = false
  protected var assignedAnchors: GUIAnchorSet = null
  var container: GUIContainer = null
  private[gui] var tooltipText: String = ""

  def this(p: Vector2, s: Vector2) {
    this()
    position = p
    size = s
    Events.EVENTMANAGER.registerEventListener(this)
  }

  def draw(screen: AbstractScreen)

  def getScreenPosition: Vector2 = {
    if (container != null) {
      return container.getScreenPosition.addNew(position)
    }
    return position
  }

  def providesActiveHierarchy: Boolean = {
    return false
  }

  def isInActiveHierarchy: Boolean = {
    if (providesActiveHierarchy) return true
    var check: GUIContainer = container
    if (check == null) return false
    if (check.providesActiveHierarchy) return true
    while (check.container != null) {
      if (check.container.providesActiveHierarchy) return true
      check = check.container
    }
    return false
  }

  def setTooltip(txt: String) {
    tooltipText = txt
  }

  def assignAnchorSet(set: GUIAnchorSet) {
    assignedAnchors = set
  }

  def applyAnchorSet(set: GUIAnchorSet) {
    if (container != null || !set.isContainerDependent) {
      set.applyAnchorSet(this, container)
    }
  }

  def applyAssignedAnchorSet {
    if (assignedAnchors != null) {
      applyAnchorSet(assignedAnchors)
    }
  }

  def update(delta: Float) {
    if (assignedAnchors != null && assignedAnchors.needsApplyOnFrame) applyAssignedAnchorSet
  }

  @EventListener(Array("WINRESIZE")) def onWinResize {
    applyAssignedAnchorSet
  }

  def onClick(button: Int) {
  }

  def onMouseIn {
    if (tooltipText ne "") {
      Tooltip.setText(tooltipText)
    }
  }

  def onMouseOut {
    if (tooltipText ne "") {
      Tooltip.setText("")
    }
  }

  def setContainer(guiContainer: GUIContainer) {
    container = guiContainer
  }
}
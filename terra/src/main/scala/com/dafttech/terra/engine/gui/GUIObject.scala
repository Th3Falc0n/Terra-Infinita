package com.dafttech.terra.engine.gui

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet
import com.dafttech.terra.engine.gui.containers.GUIContainer
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.Events
import org.lolhens.eventmanager.EventListener

abstract class GUIObject {
  var position: Vector2d = Vector2d.Zero
  var size: Vector2d = null
  var mouseHover: Boolean = false
  protected var registeredEvents: Boolean = false
  protected var assignedAnchors: GUIAnchorSet = null
  var container: GUIContainer = null
  private[gui] var tooltipText: String = ""

  def this(p: Vector2d, s: Vector2d) {
    this()
    position = p
    size = s
    Events.EVENTMANAGER.registerEventListener(this)
  }

  def draw(screen: AbstractScreen)

  def getScreenPosition: Vector2d = position + Option(container).map(_.getScreenPosition).getOrElse(Vector2d.Zero)

  def providesActiveHierarchy: Boolean = false

  def isInActiveHierarchy: Boolean = {
    if (providesActiveHierarchy) return true
    var check: GUIContainer = container
    if (check == null) return false
    if (check.providesActiveHierarchy) return true
    while (check.container != null) {
      if (check.container.providesActiveHierarchy) return true
      check = check.container
    }
    false
  }

  def setTooltip(txt: String): Unit = {
    tooltipText = txt
  }

  def assignAnchorSet(set: GUIAnchorSet): Unit = {
    assignedAnchors = set
  }

  def applyAnchorSet(set: GUIAnchorSet): Unit = {
    if (container != null || !set.isContainerDependent) {
      set.applyAnchorSet(this, container)
    }
  }

  def applyAssignedAnchorSet: Unit = {
    if (assignedAnchors != null) {
      applyAnchorSet(assignedAnchors)
    }
  }

  def update(delta: Float): Unit = {
    if (assignedAnchors != null && assignedAnchors.needsApplyOnFrame) applyAssignedAnchorSet
  }

  @EventListener(Array("WINRESIZE"))
  def onWinResize: Unit = applyAssignedAnchorSet

  def onClick(button: Int): Unit = {
  }

  def onMouseIn: Unit =
    if (tooltipText ne "") Tooltip.setText(tooltipText)

  def onMouseOut: Unit =
    if (tooltipText ne "") Tooltip.setText("")

  def setContainer(guiContainer: GUIContainer): Unit = {
    container = guiContainer
  }
}
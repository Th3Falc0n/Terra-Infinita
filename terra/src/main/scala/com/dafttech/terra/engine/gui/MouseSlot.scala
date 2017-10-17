package com.dafttech.terra.engine.gui

import com.dafttech.terra.engine.gui.elements.ElementSlot
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.inventories.Stack
import org.lolhens.eventmanager.{Event, EventListener}

object MouseSlot {

  private class MouseRenderSlot(p: Vector2) extends ElementSlot(p) {
    Events.EVENTMANAGER.registerEventListener(this)

    @EventListener(Array("MOUSEMOVE"))
    def onEventMouseMove(event: Event) {
      val x: Int = event.in.get(1, classOf[Integer])
      val y: Int = event.in.get(2, classOf[Integer])
      position.x = x
      position.y = y
    }

    override def draw(screen: AbstractScreen) {
      val p: Vector2 = getScreenPosition
      screen.batch.begin
      if (assignedStack != null) {
        (assignedStack.`type`.toGameObject.asInstanceOf[Item]).drawInventory(p, screen)
      }
      screen.batch.end
    }
  }

  private var assignedStack: Stack = null
  private var renderSlot: MouseSlot.MouseRenderSlot = new MouseSlot.MouseRenderSlot(new Vector2(0, 0))

  def init {
  }

  def getRenderSlot: ElementSlot = {
    return renderSlot
  }

  def canAssignStack: Boolean = {
    return assignedStack == null
  }

  def getAssignedStack: Stack = {
    return assignedStack
  }

  def popAssignedStack: Stack = {
    val at: Stack = assignedStack
    assignedStack = null
    renderSlot.assignStack(null)
    return at
  }

  def assignStack(stack: Stack): Boolean = {
    if (assignedStack == null) {
      assignedStack = stack
      renderSlot.assignStack(assignedStack)
      return true
    }
    return false
  }
}
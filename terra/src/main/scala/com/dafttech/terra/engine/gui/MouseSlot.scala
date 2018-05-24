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
    def onEventMouseMove(event: Event): Unit = {
      val x: Int = event.in.get[Integer](1, classOf[Integer])
      val y: Int = event.in.get[Integer](2, classOf[Integer])
      position = Vector2(x, y)
    }

    override def draw(screen: AbstractScreen): Unit = {
      val p: Vector2 = getScreenPosition
      screen.batch.begin()
      if (assignedStack != null) {
        assignedStack.prototype.toGameObject.asInstanceOf[Item].drawInventory(p, screen)
      }
      screen.batch.end()
    }
  }

  private var assignedStack: Stack = null
  private var renderSlot: MouseSlot.MouseRenderSlot = new MouseSlot.MouseRenderSlot(new Vector2(0, 0))

  def init: Unit = {
  }

  def getRenderSlot: ElementSlot = renderSlot

  def canAssignStack: Boolean = assignedStack == null

  def getAssignedStack: Stack = assignedStack

  def popAssignedStack: Stack = {
    val at: Stack = assignedStack
    assignedStack = null
    renderSlot.assignStack(null)
    at
  }

  def assignStack(stack: Stack): Boolean =
    if (assignedStack == null) {
      assignedStack = stack
      renderSlot.assignStack(assignedStack)
      true
    } else
      false
}
package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.gui.MouseSlot
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.inventories.Stack
import com.dafttech.terra.resources.Resources

import scala.concurrent.duration._

class ElementSlot(p: Vector2d) extends GUIElement(p, Vector2d(32, 32)) {
  private var cooldownTime: Float = 0
  var assignedStack: Stack = null
  var active: Boolean = false

  image = { // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    Resources.GUI.getImage("slot").runSyncUnsafe(5.seconds)
  }

  def useAssignedItem(causer: Player, pos: Vector2d, leftClick: Boolean): Boolean = {
    if (assignedStack != null && assignedStack.size > 0 && causer.world.time > cooldownTime) {
      if (!leftClick && assignedStack.use(causer, pos).size < assignedStack.size) {
        setCooldownTime(causer.world, assignedStack.prototype.toGameObject.asInstanceOf[Item].getNextUseDelay(causer, pos, leftClick).toFloat)
        return true
      }
    }
    false
  }

  def setCooldownTime(world: World, cooldownTime: Float): Unit = {
    this.cooldownTime = world.time + cooldownTime
  }

  override def onClick(button: Int): Unit = {
    if (button == 0) {
      if (MouseSlot.getAssignedStack != null && assignedStack == null) {
        assignedStack = MouseSlot.popAssignedStack
      }
      else if (MouseSlot.canAssignStack && assignedStack != null) {
        MouseSlot.assignStack(assignedStack)
        assignedStack = null
      }
      else if (MouseSlot.getAssignedStack != null && assignedStack != null) {
        val at: Stack = MouseSlot.popAssignedStack
        MouseSlot.assignStack(assignedStack)
        assignedStack = at
      }
    }
  }

  override def draw(screen: AbstractScreen): Unit = {
    screen.batch.setColor(if (active) Color.YELLOW else Color.WHITE)
    super.draw(screen)
    val p: Vector2d = getScreenPosition
    screen.batch.begin()
    if (assignedStack != null) {
      assignedStack.prototype.toGameObject.asInstanceOf[Item].drawInventory(p, screen)
      Resources.GUI_FONT.setColor(if (active) Color.YELLOW else Color.WHITE)
      Resources.GUI_FONT.draw(screen.batch, "" + assignedStack.size, p.x.toFloat, 6 + p.y.toFloat)
    }
    screen.batch.end()
  }

  def assignStack(stack: Stack): Unit = {
    assignedStack = stack
  }
}
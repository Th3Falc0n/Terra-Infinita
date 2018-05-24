package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.gui.MouseSlot
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.inventories.{Inventory, Stack}
import com.dafttech.terra.resources.Resources

class ElementSlotInventory(p: Vector2, val assignedInventory: Inventory) extends GUIElement(p, new Vector2(32, 32)) {
  private var cooldownTime: Float = 0
  var assignedStack: Stack = null
  var active: Boolean = false

  def useAssignedItem(causer: Player, pos: Vector2, leftClick: Boolean): Boolean = {
    if (assignedStack != null && assignedStack.size > 0 && causer.worldObj.time > cooldownTime)
      if (!leftClick && assignedStack.use(causer, pos).size < assignedStack.size) {
        setCooldownTime(causer.worldObj, assignedStack.prototype.toGameObject.asInstanceOf[Item].getNextUseDelay(causer, pos, leftClick).toFloat)
        return true
      }
    false
  }

  def setCooldownTime(world: World, cooldownTime: Float): Unit = {
    this.cooldownTime = world.time + cooldownTime
  }

  override def onClick(button: Int) {
    if (button == 0) {
      if (MouseSlot.getAssignedStack != null) {
      }
      else if (MouseSlot.canAssignStack && assignedStack != null) {
        MouseSlot.assignStack(assignedStack)
        assignedInventory.remove(assignedStack)
      }
    }
  }

  override def draw(screen: AbstractScreen): Unit = {
    screen.batch.setColor(if (active) Color.YELLOW else Color.WHITE)
    super.draw(screen)
    val p: Vector2 = getScreenPosition
    screen.batch.begin()
    if (assignedStack != null) {
      assignedStack.prototype.toGameObject.asInstanceOf[Item].drawInventory(p, screen)
      Resources.GUI_FONT.draw(screen.batch, assignedStack.size + "x " + assignedStack.prototype.toGameObject.getName, 40 + p.x.toFloat, 12 + p.y.toFloat)
    }
    screen.batch.end()
  }

  def assignStack(stack: Stack): Unit = {
    assignedStack = stack
  }
}
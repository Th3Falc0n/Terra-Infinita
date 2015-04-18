package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.engine.gui.MouseSlot
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.inventories.{Inventory, Stack}
import com.dafttech.terra.resources.Resources

class ElementSlotInventory extends GUIElement {
  private var cooldownTime: Float = 0
  var assignedStack: Stack = null
  var assignedInventory: Inventory = null
  var active: Boolean = false

  def this(p: Vector2, i: Inventory) {
    this()
    `super`(p, new Vector2(32, 32))
    assignedInventory = i
    image = Resources.GUI.getImage("slot")
  }

  def useAssignedItem(causer: Player, pos: Vector2, leftClick: Boolean): Boolean = {
    if (assignedStack != null && assignedStack.amount > 0 && causer.worldObj.time > cooldownTime) {
      if ((!leftClick && assignedStack.use(causer, pos))) {
        setCooldownTime(causer.worldObj, (assignedStack.`type`.toGameObject.asInstanceOf[Item]).getNextUseDelay(causer, pos, leftClick))
        return true
      }
    }
    return false
  }

  def setCooldownTime(world: World, cooldownTime: Float) {
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

  override def draw(screen: AbstractScreen) {
    screen.batch.setColor(if (active) Color.YELLOW else Color.WHITE)
    super.draw(screen)
    val p: Vector2 = getScreenPosition
    screen.batch.begin
    if (assignedStack != null) {
      (assignedStack.`type`.toGameObject.asInstanceOf[Item]).drawInventory(p, screen)
      Resources.GUI_FONT.draw(screen.batch, assignedStack.amount + "x " + assignedStack.`type`.toGameObject.getName, 40 + p.x, 12 + p.y)
    }
    screen.batch.end
  }

  def assignStack(stack: Stack) {
    assignedStack = stack
  }
}
package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.engine.gui.MouseSlot
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.inventories.Stack
import com.dafttech.terra.resources.Resources

class ElementSlot extends GUIElement {
  private var cooldownTime: Float = 0
  var assignedStack: Stack = null
  var active: Boolean = false

  def this(p: Vector2) {
    this()
    `super`(p, new Vector2(32, 32))
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

  override def draw(screen: AbstractScreen) {
    screen.batch.setColor(if (active) Color.YELLOW else Color.WHITE)
    super.draw(screen)
    val p: Vector2 = getScreenPosition
    screen.batch.begin
    if (assignedStack != null) {
      (assignedStack.`type`.toGameObject.asInstanceOf[Item]).drawInventory(p, screen)
      Resources.GUI_FONT.setColor(if (active) Color.YELLOW else Color.WHITE)
      Resources.GUI_FONT.draw(screen.batch, "" + assignedStack.amount, p.x, 6 + p.y)
    }
    screen.batch.end
  }

  def assignStack(stack: Stack) {
    assignedStack = stack
  }
}
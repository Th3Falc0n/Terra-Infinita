package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.resources.Resources

class ElementSkill extends GUIElement {
  private var label: String = ""
  private var player: Player = null
  private var skillID: Int = 0
  var active: Boolean = false

  def this(p: Vector2, pl: Player, l: String, sid: Int) {
    this()
    `super`(p, new Vector2(32, 32))
    image = Resources.GUI.getImage("slot")
    label = l
    player = pl
    skillID = sid
  }

  override def onClick(button: Int) {
  }

  override def draw(screen: AbstractScreen) {
    screen.batch.setColor(if (active) Color.YELLOW else Color.WHITE)
    super.draw(screen)
    val p: Vector2 = getScreenPosition
    screen.batch.begin
    if (player.getSkillForID(skillID) != null) {
      player.getSkillForID(skillID).drawInventory(p, screen)
      Resources.GUI_FONT.setColor(if (active) Color.YELLOW else Color.WHITE)
      Resources.GUI_FONT.draw(screen.batch, label, p.x, 6 + p.y)
    }
    screen.batch.end
  }
}
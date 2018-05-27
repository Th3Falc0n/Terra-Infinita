package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.resources.Resources
import scala.concurrent.duration._

class ElementSkill(p: Vector2, val player: Player, val label: String, val skillID: Int) extends GUIElement(p, Vector2(32, 32)) {
  var active: Boolean = false

  image = { // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    Resources.GUI.getImage("slot").runSyncUnsafe(5.seconds)
  }

  override def onClick(button: Int): Unit = {
  }

  override def draw(screen: AbstractScreen): Unit = {
    screen.batch.setColor(if (active) Color.YELLOW else Color.WHITE)
    super.draw(screen)
    val p: Vector2 = getScreenPosition
    screen.batch.begin()
    if (player.getSkillForID(skillID) != null) {
      player.getSkillForID(skillID).drawInventory(p, screen)
      Resources.GUI_FONT.setColor(if (active) Color.YELLOW else Color.WHITE)
      Resources.GUI_FONT.draw(screen.batch, label, p.x.toFloat, 6 + p.y.toFloat)
    }
    screen.batch.end()
  }
}
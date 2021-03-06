package com.dafttech.terra.game.world.interaction.skills

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.game.world.interaction.Skill
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class SkillHealingStrike extends Skill {
  override def getImage: Task[TextureRegion] = Resources.SKILLS.getImageTask("smashing_strike")
}
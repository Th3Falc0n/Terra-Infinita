package com.dafttech.terra.game.world.interaction.skills

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.game.world.interaction.Skill
import com.dafttech.terra.resources.Resources

class SkillStab extends Skill {
  override def getImage: TextureRegion = Resources.SKILLS.getImage("stab")
}
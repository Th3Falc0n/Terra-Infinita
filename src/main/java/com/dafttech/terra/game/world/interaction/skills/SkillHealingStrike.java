package com.dafttech.terra.game.world.interaction.skills;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.interaction.Skill;
import com.dafttech.terra.resources.Resources$;

public class SkillHealingStrike extends Skill {
    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.SKILLS().getImage("smashing_strike");
    }
}

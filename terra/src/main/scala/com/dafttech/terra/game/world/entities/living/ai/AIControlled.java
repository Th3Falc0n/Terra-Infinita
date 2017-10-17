package com.dafttech.terra.game.world.entities.living.ai;

import com.dafttech.terra.engine.input.InputHandler$;

public class AIControlled extends ArtificialIntelligence {
    @Override
    public void update(float delta) {
        if (InputHandler$.MODULE$.isKeyDown("LEFT")) assignedEntity.walkLeft();
        if (InputHandler$.MODULE$.isKeyDown("RIGHT")) assignedEntity.walkRight();
        if (InputHandler$.MODULE$.isKeyDown("JUMP") && !assignedEntity.isInAir()) assignedEntity.jump();
    }
}

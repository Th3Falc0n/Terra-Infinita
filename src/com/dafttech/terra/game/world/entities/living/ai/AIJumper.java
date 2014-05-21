package com.dafttech.terra.game.world.entities.living.ai;

public class AIJumper extends ArtificialIntelligence {
    @Override
    public void update(float delta) {
        if (!assignedEntity.isInAir()) assignedEntity.jump();
    }
}

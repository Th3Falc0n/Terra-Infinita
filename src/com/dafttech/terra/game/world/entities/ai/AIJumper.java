package com.dafttech.terra.game.world.entities.ai;

public class AIJumper extends ArtificalIntelligence {
    @Override
    public void update(float delta) {
        if(!assignedEntity.isInAir()) assignedEntity.jump();
    }
}

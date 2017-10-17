package com.dafttech.terra.game.world.entities.models;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.living.ai.ArtificialIntelligence;

public abstract class EntityLivingAI extends EntityLiving {
    public EntityLivingAI(Vector2 pos, World world, Vector2 s, ArtificialIntelligence ai) {
        super(pos, world, s);

        assignedAI = ai;
        assignedAI.setEntity(this);
    }

    ArtificialIntelligence assignedAI;

    float deltaCount = 0;

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        deltaCount += delta;

        if (deltaCount > 0.1f) {
            assignedAI.update(deltaCount);
            deltaCount = 0;
        }
    }
}

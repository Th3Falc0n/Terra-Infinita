package com.dafttech.terra.game.world.entities.models;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public abstract class EntityThrown extends Entity {
    public EntityThrown(Vector2 pos, World world, Vector2 size) {
        super(pos, world, size);
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);
        if (velocity.len2() > 0.1f) {
            setRotation(velocity.angle());
        }
    }

    @Override
    public float getInAirFriction() {
        return 0.025f;
    }
}

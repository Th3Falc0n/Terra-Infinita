package com.dafttech.terra.game.world.entities.models;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.resources.Resources;

public abstract class EntityThrown extends Entity {
    public EntityThrown(Vector2 pos, World world, Vector2 size) {
        super(pos, world, size);
    }
    
    @Override
    public void update(World world, float delta) {
        super.update(world, delta);
        
        setRotation(velocity.angle());
    }
    
    @Override
    public float getInAirFriction() {
        return 0.025f;
    }
}

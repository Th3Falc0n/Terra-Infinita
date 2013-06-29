package com.dafttech.terra.world.entities.particles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Entity;

public class Particle extends Entity {
    float lifetimeMax = 25;
    float lifetime = 25;

    public Particle(Vector2 pos, World world, float life, Vector2 s) {
        super(pos, world, s);

        lifetime = life;
        lifetimeMax = life;
    }

    @Override
    public TextureRegion getImage() {
        return null;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        lifetime -= delta;

        if (lifetime < 0) {
            this.getWorld().removeEntity(this);
        }
    }

    @Override
    public void checkTerrainCollisions(World world) {

    }

}
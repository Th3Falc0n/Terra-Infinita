package com.dafttech.terra.game.world.entities.particles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

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

    public float getInAirFriction() {
        return 0.025f;
    }

}

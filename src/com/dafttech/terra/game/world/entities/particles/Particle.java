package com.dafttech.terra.game.world.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public abstract class Particle extends Entity {
    float lifetimeMax = 25;
    float lifetime = lifetimeMax;
    
    boolean fadeOut = false;

    public Particle(Vector2 pos, World world, float life, Vector2 s) {
        super(pos, world, s);

        lifetimeMax = life;
        lifetime = lifetimeMax;
    }

    public void setFadeOut(boolean fo) {
        fadeOut = fo;
    }
    
    @Override
    public TextureRegion getImage() {
        return null;
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        lifetime -= delta;

        if(fadeOut) {
            setAlpha(1f - (lifetime/lifetimeMax));
        }
        
        if (lifetime < 0) {
            this.getWorld().removeEntity(this);
        }
    }

    @Override
    public float getInAirFriction() {
        return 0.025f;
    }

}

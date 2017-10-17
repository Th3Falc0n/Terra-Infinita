package com.dafttech.terra.game.world.entities.particles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources$;

public class ParticleRain extends Particle {

    public ParticleRain(Vector2 pos, World world) {
        super(pos, world, 4, new Vector2(0.1f, 1f));
        setHasGravity(true);
        setGravityFactor(0.05f);
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        if (!inAir) {
            world.removeEntity(this);
        }
    }

    @Override
    public boolean alignToVelocity() {
        return true;
    }

    @Override
    public float getVelocityOffsetAngle() {
        return 90;
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("raindrop");
    }
}

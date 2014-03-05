package com.dafttech.terra.game.world.entities.particles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class ParticleRain extends Particle {
    float img = 0;

    public ParticleRain(Vector2 pos, World world) {
        super(pos, world, 4, new Vector2(3f, 3f));
        setHasGravity(true);
        setGravityFactor(0.05f);
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        img += delta * 10;
        if ((int) img > 3) img = 0;

        if (Math.abs(velocity.x) <= 0.1 && Math.abs(velocity.y) <= 0.1) {
            worldObj.removeEntity(this);
        }
    }

    /*
     * @Override public void checkTerrainCollisions(World world) {
     * 
     * }
     */

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("rain", (int) img);
    }
}

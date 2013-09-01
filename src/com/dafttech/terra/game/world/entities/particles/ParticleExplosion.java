package com.dafttech.terra.game.world.entities.particles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class ParticleExplosion extends Particle {

    public ParticleExplosion(Vector2 pos, World world, int radius) {
        super(pos, world, 0.6f, new Vector2(radius * 2, radius * 2));
        setGravityFactor(0);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("explosion");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (int i = 0; i < 10; i++) {
            worldObj.addEntity(new ParticleSpark(position.clone().add(new Random().nextFloat() * getSize().x * BLOCK_SIZE,
                    new Random().nextFloat() * getSize().y * BLOCK_SIZE), worldObj));
        }
        getSize().add(delta * 8, delta * 8);
        setPosition(getPosition().add(delta * -4 * BLOCK_SIZE, delta * -4 * BLOCK_SIZE));
    }
}

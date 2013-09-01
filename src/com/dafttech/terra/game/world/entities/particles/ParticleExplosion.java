package com.dafttech.terra.game.world.entities.particles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class ParticleExplosion extends Particle {
    PointLight light;
    int radius;

    public ParticleExplosion(Vector2 pos, World world, int radius) {
        super(pos, world, 0.6f, new Vector2(radius * 2, radius * 2));
        this.radius = radius;
        setGravityFactor(0);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("explosion");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        worldObj.addEntity(new ParticleSpark(position.clone().add(new Random().nextFloat() * getSize().x * BLOCK_SIZE,
                new Random().nextFloat() * getSize().y * BLOCK_SIZE), worldObj));
        getSize().add(delta * 8, delta * 8);
        setPosition(getPosition().add(delta * -4 * BLOCK_SIZE, delta * -4 * BLOCK_SIZE));
    }

    @Override
    public boolean isLightEmitter() {
        return true;
    }

    public PointLight getEmittedLight() {
        if (light == null) {
            light = new PointLight(position, 60);
            light.setColor(new Color(1, 0.9f, 0.9f, 0.4f));
        }
        light.setSize((getSize().x + getSize().y) * 10 + BLOCK_SIZE);
        light.setPosition(new Vector2(position.clone()).add(light.getSize() / 2, light.getSize() / 2));
        return light;
    }
}

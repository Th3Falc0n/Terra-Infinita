package com.dafttech.terra.game.world.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

import java.util.Random;

public class ParticleExplosion extends Particle {
    PointLight light;
    int radius;
    Vector2 midpos;

    public ParticleExplosion(Vector2 pos, World world, int radius) {
        super(pos, world, 0.3f, new Vector2(radius * 2, radius * 2));
        this.radius = radius;
        midpos = pos;
        setGravityFactor(0);
        setMidPos(midpos);
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("explosion");
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);
        Random rnd = new Random();
        int blockSRad = radius * Options.BLOCK_SIZE() * 3;
        new ParticleSpark(midpos.$plus(-blockSRad, -blockSRad).$plus(rnd.nextFloat() * blockSRad * 2, rnd.nextFloat() * blockSRad * 2), worldObj());
        setSize(getSize().$plus(delta * 30, delta * 30));
        setMidPos(midpos);
    }

    @Override
    public boolean isLightEmitter() {
        return true;
    }

    @Override
    public PointLight getEmittedLight() {
        if (light == null) {
            light = new PointLight(getPosition(), 60);
            light.setColor(new Color(1, 0.9f, 0.9f, 0.4f));
        }
        light.setSize((getSize().xFloat() + getSize().yFloat()) * 6 + Options.BLOCK_SIZE());
        return light;
    }
}

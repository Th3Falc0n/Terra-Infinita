package com.dafttech.terra.game.world.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

public class ParticleSpark extends Particle {
    PointLight light;

    float size;

    public ParticleSpark(Vector2 pos, World world) {
        super(pos, world, 0.6f + (0.75f * TerraInfinita.rnd().nextFloat()), new Vector2(0.5f, 0.5f));

        size = TerraInfinita.rnd().nextFloat() * 0.2f + 0.1f;

        setSize(new Vector2(size, getSize().x()));

        setHasGravity(true);
        setGravityFactor(0.05f);
        setVelocity(new Vector2(4f * (0.5f - TerraInfinita.rnd().nextFloat()), 4f * (0.5f - TerraInfinita.rnd().nextFloat())));
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("flame");
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        setSize(new Vector2(size * (lifetime / lifetimeMax), getSize().x()));

        if (light != null) {
            light.setSize(55 * 2 * (lifetime / lifetimeMax));
            light.setPosition(getPosition().$plus(getSize().x() * Options.BLOCK_SIZE() / 2, getSize().y() * Options.BLOCK_SIZE() / 2));
        }
    }

    @Override
    public boolean isLightEmitter() {
        return true;
    }

    @Override
    public void checkTerrainCollisions(World world) {

    }

    @Override
    public PointLight getEmittedLight() {
        if (light == null) {
            light = new PointLight(getPosition(), 55);
            light.setColor(new Color(1, 0.9f, 0.9f, 0.4f));
        }

        return light;
    }

}

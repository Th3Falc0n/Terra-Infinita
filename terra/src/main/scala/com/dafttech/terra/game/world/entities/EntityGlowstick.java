package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

import java.util.Random;

public class EntityGlowstick extends Entity {
    PointLight light;

    float gsRotation = 0;

    public EntityGlowstick(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.5f, 1.5f));

        setGravityFactor(0.125f);
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("glowstick");
    }

    @Override
    public boolean collidesWith(Entity e) {
        return false;
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        gsRotation += velocity().x() * delta * 50;

        if (light == null) {
            light = new PointLight(getPosition(), 95);
            light.setColor(new Color(0.1f, 1, 0.1f, 1));
        }

        light.setSize(90 + new Random().nextInt(10));

        light.setPosition(getPosition().$plus(size().x() * Options.BLOCK_SIZE() / 2, size().y() * Options.BLOCK_SIZE() / 2));

        setRotation((float) (velocity().rotation() + Math.PI / 2 + gsRotation));
    }

    @Override
    public double getInAirFriction() {
        return 0.025;
    }

    @Override
    public boolean isLightEmitter() {
        return true;
    }

    @Override
    public PointLight getEmittedLight() {
        return light;
    }
}
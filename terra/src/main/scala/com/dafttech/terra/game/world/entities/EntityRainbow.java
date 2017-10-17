package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.models.EntityThrown;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

import java.util.Random;

public class EntityRainbow extends EntityThrown {
    PointLight light;

    public EntityRainbow(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.5f, 1.5f));

        setGravityFactor(0.125f);

        isDynamicEntity = true;
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("rainbow");
    }

    @Override
    public boolean collidesWith(Entity e) {
        return false;
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        if (light == null) {
            light = new PointLight(getPosition(), 95);
            light.setColor(new Color(1, 1, 1, 1));
        }

        light.setSize(90 + new Random().nextInt(10));

        light.setPosition(getPosition().add(size.x() * Options.BLOCK_SIZE() / 2, size.y() * Options.BLOCK_SIZE() / 2));

        if (Math.abs(velocity.x()) <= 0.1 && Math.abs(velocity.y()) <= 0.1) {
            worldObj.removeEntity(this);
        }
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
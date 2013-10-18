package com.dafttech.terra.game.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class EntityRainbow extends Entity {
    PointLight light;

    public EntityRainbow(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.5f, 1.5f));

        setGravityFactor(0.125f);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("rainbow");
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        Vector2 screenVec = this.getPosition().toRenderPosition(pointOfView.getPosition());

        Vector2 v = new Vector2(velocity);

        screen.batch.draw(this.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE * size.x / 2, BLOCK_SIZE * size.y / 2, BLOCK_SIZE * size.x,
                BLOCK_SIZE * size.y, 1, 1, v.angle());
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (light == null) {
            light = new PointLight(getPosition(), 95);
            light.setColor(new Color(1, 1, 1, 1));
        }

        light.setSize(90 + new Random().nextInt(10));

        light.setPosition(getPosition().add(size.x * BLOCK_SIZE / 2, size.y * BLOCK_SIZE / 2));

        if (Math.abs(velocity.x) <= 0.1 && Math.abs(velocity.y) <= 0.1) {
            worldObj.removeEntity(this);
        }
    }

    @Override
    public float getInAirFriction() {
        return 0.025f;
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
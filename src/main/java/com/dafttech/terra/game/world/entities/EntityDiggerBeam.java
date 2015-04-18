package com.dafttech.terra.game.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.models.EntityThrown;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.resources.Resources;

public class EntityDiggerBeam extends EntityThrown {
    PointLight light;

    public EntityDiggerBeam(Vector2 pos, World world) {
        super(pos, world, new Vector2(4f, 2f));
        setGravityFactor(0);

        isDynamicEntity = true;
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("beamDig");
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        if (light == null) {
            light = new PointLight(getPosition(), 95);
            light.setColor(new Color(0, 1, 0.3f, 1));
        }

        light.setSize(90 + new Random().nextInt(10));

        light.setPosition(getPosition().add(size.x * BLOCK_SIZE / 2, size.y * BLOCK_SIZE / 2));

        if (Math.abs(velocity.x) <= 0.1 && Math.abs(velocity.y) <= 0.1) {
            worldObj.removeEntity(this);
        }
    }

    @Override
    public boolean collidesWith(Entity e) {
        return false;
    }

    @Override
    public void onTerrainCollision(Tile tile) {
        if (!tile.isAir()) {
            worldObj.destroyTile(tile.getPosition().x, tile.getPosition().y, this).addVelocity(velocity.mulNew(-1));
            this.remove();
        }
    }

    @Override
    public float getInAirFriction() {
        return 0;
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

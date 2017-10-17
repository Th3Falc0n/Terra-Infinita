package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.particles.ParticleSpark;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileFire;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

import java.util.Random;

public class EntityFlamingArrow extends EntityArrow {
    PointLight light;

    public EntityFlamingArrow(Vector2 pos, World world) {
        super(pos, world);
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("arrow");
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        if (light == null) {
            light = new PointLight(getPosition(), 95);
            light.setColor(new Color(255, 200, 40, 255));
        }
        light.setPosition(getPosition().$plus(size.x() * Options.BLOCK_SIZE() / 2, size.y() * Options.BLOCK_SIZE() / 2));

        light.setSize(90 + new Random().nextInt(10));

        for (int i = 0; i < 5; i++) {
            if (TerraInfinita.rnd().nextDouble() < delta * velocity.length() * 0.5f) {
                new ParticleSpark(getPosition(), worldObj);
            }
        }
    }

    @Override
    public void onTerrainCollision(Tile tile) {
        super.onTerrainCollision(tile);

        placeBlockOnHit(tile.getPosition().x(), tile.getPosition().y());
    }

    public void placeBlockOnHit(int x, int y) {
        TileFire.createFire(worldObj, x, y);
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

package com.dafttech.terra.game.world.tiles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.resources.Resources;

public class TileFire extends Tile {
    PointLight light;
    float spreadCounter = 0.2f;
    float spreadCounterMax = 0.2f;
    float lifetime = 0;
    int distance = 3;

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("fire");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (lifetime == 0) lifetime = new Random().nextFloat() * 2f + 1;

        spreadCounter -= delta;
        lifetime -= delta;

        if (spreadCounter <= 0) {
            int spreadX = position.x + new Random().nextInt(distance * 2) - distance;
            int spreadY = position.y + new Random().nextInt(distance * 2) - distance;
            Tile spreadTile = world.getTile(spreadX, spreadY);
            if (spreadTile instanceof ITileInteraction) {
                if (((ITileInteraction) spreadTile).isFlammable()) {
                    spreadCounter = spreadCounterMax;
                    world.setTile(spreadX, spreadY, new TileFire(), true);
                }
            }
        }
        if (lifetime <= 0) {
            world.setTile(position, null, true);
        }

        if (light == null) light = new PointLight(position.toEntityPos(), 95);

        light.setPosition(position.toEntityPos().add(BLOCK_SIZE / 2, BLOCK_SIZE / 2));
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
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

package com.dafttech.terra.game.world.tiles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.EntityLiving;
import com.dafttech.terra.resources.Resources;

public class TileFire extends TileFalling {
    PointLight light;
    float spreadCounter = 0.2f;
    float spreadCounterMax = 0.2f;
    float lifetime = 0;
    int spreadDistance = 3;
    float speedMod = 0.05f;

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("fire");
    }

    @Override
    public void onTick(World world) {
        if (lifetime == 0) lifetime = new Random().nextFloat() * 2f + 1;

        spreadCounter -= speedMod;
        lifetime -= speedMod;

        if (spreadCounter <= 0) {
            int spreadX = position.x + new Random().nextInt(spreadDistance * 2) - spreadDistance;
            int spreadY = position.y + new Random().nextInt(spreadDistance * 2) - spreadDistance;
            Tile spreadTile = world.getTile(spreadX, spreadY);
            if (spreadTile instanceof ITileInteraction && ((ITileInteraction) spreadTile).isFlammable()) {
                spreadCounter = spreadCounterMax;
                world.setTile(spreadX, spreadY, new TileFire(), true);
            }
        }
        if (lifetime <= 0) {
            world.setTile(position, null, true);
        }

        if (light == null) light = new PointLight(position.toEntityPos(), 95);

        light.setPosition(position.toEntityPos().add(BLOCK_SIZE / 2, BLOCK_SIZE / 2));
    }

    public static boolean createFire(World world, int x, int y) {
        Tile spreadTile = world.getTile(x, y);
        if (spreadTile == null || (spreadTile instanceof ITileInteraction && ((ITileInteraction) spreadTile).isFlammable())) {
            world.setTile(x, y, new TileFire(), true);
            return true;
        }
        return false;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        if (entity instanceof EntityLiving) ((EntityLiving) entity).damage(0.01f);
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

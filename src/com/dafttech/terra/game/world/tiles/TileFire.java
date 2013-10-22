package com.dafttech.terra.game.world.tiles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.Vector2i;
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
    boolean spread = true;

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("fire");
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        if (lifetime == 0) lifetime = new Random().nextFloat() * 2f + 1;

        spreadCounter -= speedMod;
        lifetime -= speedMod;

        if (spread && spreadCounter <= 0) {
            Vector2i spreadPosition = getPosition().add(new Random().nextInt(spreadDistance * 2) - spreadDistance,
                    new Random().nextInt(spreadDistance * 2) - spreadDistance);
            Tile spreadTile = world.getTile(spreadPosition);
            if (spreadTile.isFlammable()) {
                spreadCounter = spreadCounterMax;
                world.setTile(spreadPosition, new TileFire(), true);
            }
        }
        if (lifetime <= 0) {
            world.setTile(getPosition(), null, true);
        }

        if (light == null) light = new PointLight(getPosition().toEntityPos(), 95);

        light.setPosition(getPosition().toEntityPos().add(BLOCK_SIZE / 2, BLOCK_SIZE / 2));
    }

    public static boolean createFire(World world, int x, int y) {
        Tile spreadTile = world.getTile(x, y);
        if (spreadTile.isFlammable() || areSurroundingTilesFlammable(world, x, y)) {
            world.setTile(x, y, new TileFire(), true);
            return true;
        } else if (spreadTile.isReplacable()) {
            world.setTile(x, y, new TileFire().dontSpread(), true);
            return true;
        }
        return false;
    }

    public static boolean areSurroundingTilesFlammable(World world, int x, int y) {
        return world.getTile(x + 1, y).isFlammable() || world.getTile(x - 1, y).isFlammable() || world.getTile(x, y + 1).isFlammable()
                || world.getTile(x, y - 1).isFlammable();
    }

    public TileFire dontSpread() {
        spread = false;
        return this;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public int getTemperature() {
        return 100;
    }

    @Override
    public boolean isCollidableWith(Entity entity) {
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

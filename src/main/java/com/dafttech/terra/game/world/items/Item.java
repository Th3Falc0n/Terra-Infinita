package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInventory;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.game.world.items.persistence.GameObject;

public abstract class Item extends GameObject implements IDrawableInventory {
    public abstract TextureRegion getImage();

    public abstract boolean use(EntityLiving causer, Vector2 position);

    public float getNextUseDelay(EntityLiving causer, Vector2 position, boolean leftClick) {
        return 0;
    }

    public int getUsedItemNum(EntityLiving causer, Vector2 position) {
        return 1;
    }

    public boolean isLightEmitter() {
        return false;
    }

    public PointLight getEmittedLight() {
        return null;
    }

    public EntityItem spawnAsEntity(Vector2 position, World world) {
        return new EntityItem(position, world, new Vector2(0.5f, 0.5f), this);
    }

    @Override
    public void drawInventory(Vector2 pos, AbstractScreen screen) {
        screen.batch.draw(getImage(), pos.x + 4, pos.y + 4 + 12 * (1 - (getImage().getRegionHeight() / (float) getImage().getRegionWidth())), 24,
                24 * (getImage().getRegionHeight() / (float) getImage().getRegionWidth()));
    }

    public int maxStackSize() {
        return 99;
    }
}

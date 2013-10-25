package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInventory;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.items.persistence.GameObject;

public abstract class Item extends GameObject implements IDrawableInventory {
    public abstract TextureRegion getImage();

    public abstract boolean use(Player causer, Vector2 position);

    public float getNextUseDelay(Player causer, Vector2 position, boolean leftClick) {
        return 0;
    }

    public int getUsedItemNum(Player causer, Vector2 position, boolean leftClick) {
        return 1;
    }

    public boolean isLightEmitter() {
        return false;
    }

    public float getWeight() {
        return 1;
    }

    public PointLight getEmittedLight() {
        return null;
    }

    public EntityItem spawnAsEntity(Vector2 position, World world) {
        return new EntityItem(position, world, new Vector2(0.5f, 0.5f), this);
    }

    @Override
    public void drawInventory(Vector2 pos, AbstractScreen screen) {
        screen.batch.draw(getImage(), pos.x + 8, pos.y + 8, 16, 16);
    }

    public boolean canBeStackedWith(Item item) {
        return item.getClass() == getClass();
    }

    public boolean leftClick(Player causer, Vector2 pos) {
        return false;
    }
}

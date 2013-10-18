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
    

    public boolean isLightEmitter() {
        return false;
    }

    public float getWeight() {
        return 1;
    }

    public PointLight getEmittedLight() {
        return null;
    }

    public void spawnAsEntity(Vector2 position, World world) {
        new EntityItem(position, world, new Vector2(0.5f, 0.5f), this);
    }

    @Override
    public void drawInventory(AbstractScreen screen, Vector2 position) {
        screen.batch.draw(getImage(), position.x + 8, position.y + 8, 16, 16);
    }

    public boolean canBeStackedWith(Item item) {
        return item.getClass() == getClass();
    }
}

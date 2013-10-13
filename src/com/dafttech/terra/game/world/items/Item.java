package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInventory;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.EntityItem;

public abstract class Item implements IDrawableInventory {
    public abstract TextureRegion getImage();

    public boolean isLightEmitter() {
        return false;
    }

    public PointLight getEmittedLight() {
        return null;
    }

    public int getMaxStackSize() {
        return 99;
    }


    public void spawnAsEntity(Vector2 position, World world) {
        world.addEntity(new EntityItem(position, world, new Vector2(0.5f, 0.5f), this));
    }
    
    @Override
    public void drawInventory(AbstractScreen screen, Vector2 position) {
        // TODO Auto-generated method stub
        
    }
}

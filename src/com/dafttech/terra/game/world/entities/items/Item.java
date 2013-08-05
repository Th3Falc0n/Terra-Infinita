package com.dafttech.terra.game.world.entities.items;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawableInventory;
import com.dafttech.terra.resources.Resources;

public abstract class Item extends Entity implements IDrawableInventory {
    static Map<Integer, Class<?>> registry = new HashMap<Integer, Class<?>>();

    public static void registerItem(Integer id, Class<?> mat) {
        registry.put(id, mat);
    }

    public static Item getInstanceOf(Integer id) {
        try {
            return (Item) registry.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Item(Vector2 pos, World world, Vector2 size) {
        super(pos, world, size);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("error");
    }

    @Override
    public void drawInventory(AbstractScreen screen, Vector2 position) {
        // TODO Auto-generated method stub

    }
}

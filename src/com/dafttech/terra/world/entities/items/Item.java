package com.dafttech.terra.world.entities.items;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawableInventory;
import com.dafttech.terra.graphics.lighting.Light;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.entities.Player;

public abstract class Item extends Entity implements IDrawableInventory {
    static Map<Integer, Class<?>> registry = new HashMap<Integer, Class<?>>();

    public static void registerItem(Integer id, Class<?> mat) {
        registry.put(id, mat);
    }

    PointLight light = new PointLight(position, 12);

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
    public void drawInventory(AbstractScreen screen, Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(Player player, float delta) {
        // TODO Auto-generated method stub
        super.update(player, delta);
        light.setPosition(position);
    }
}

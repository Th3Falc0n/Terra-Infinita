package com.dafttech.terra.world;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IRenderable;
import com.dafttech.terra.graphics.SubtileRenderer;
import com.dafttech.terra.graphics.renderers.SubtileRendererMask;
import com.dafttech.terra.world.entity.Player;
import com.dafttech.terra.world.subtiles.SubtileGrass;

public class Subtile implements IRenderable {
    static Map<Integer, Class<?>> registry = new HashMap<Integer, Class<?>>();

    public static void registerSubtile(Integer id, Class<?> mat) {
        registry.put(id, mat);
    }

    public static Subtile getInstanceOf(Integer id) {
        try {
            return (Subtile) registry.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void init() {
        registerSubtile(1, SubtileGrass.class);
    }

    Tile tile;
    TextureRegion image;

    public Subtile(Tile t, TextureRegion textureRegion) {
        tile = t;
        image = textureRegion;
    }

    public SubtileRenderer getRenderer() {
        return SubtileRendererMask.$Instance;
    }

    public TextureRegion getImage() {
        return image;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile t) {
        tile = t;
    }

    public boolean canBePlacedOn(Tile tile) {
        return true;
    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        getRenderer().draw(screen, this, player);
    }

    @Override
    public void update(Player player, float delta) {
        // TODO Auto-generated method stub

    }
}

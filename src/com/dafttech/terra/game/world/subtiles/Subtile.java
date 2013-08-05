package com.dafttech.terra.game.world.subtiles;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.graphics.renderer.SubtileRenderer;
import com.dafttech.terra.graphics.renderer.SubtileRendererMask;

public class Subtile implements IDrawable {
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
        registerSubtile(2, SubtileBone.class);
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
    public void draw(AbstractScreen screen, Entity pointOfView) {
        getRenderer().draw(screen, this, pointOfView);
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }
}

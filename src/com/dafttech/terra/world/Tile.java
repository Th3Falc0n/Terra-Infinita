package com.dafttech.terra.world;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.world.materials.TileDirt;
import com.dafttech.terra.world.materials.TileGrass;
import com.dafttech.terra.world.materials.TileStone;
import com.dafttech.terra.world.renderers.RendererBlock;

@SuppressWarnings("rawtypes")
public class Tile {
    static Map<Integer, Class> registry = new HashMap<Integer, Class>();

    public static void registerTile(Integer id, Class mat) {
        registry.put(id, mat);
    }

    public static Tile getInstanceOf(Integer id) {
        try {
            return (Tile) registry.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void init() {
        registerTile(1, TileDirt.class);
        registerTile(2, TileGrass.class);
        registerTile(3, TileStone.class);
    }

    Position position;
    TextureRegion image;

    public Tile(Position pos, TextureRegion textureRegion) {
        position = pos;
        image = textureRegion;
    }

    public Renderer getRenderer() {
        return RendererBlock.$Instance;
    }

    public TextureRegion getImage() {
        return image;
    }

    public Position getPosition() {
        return position;
    }
}

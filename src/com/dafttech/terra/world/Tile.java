package com.dafttech.terra.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IRenderable;
import com.dafttech.terra.graphics.TileRenderer;
import com.dafttech.terra.graphics.renderers.TileRendererBlock;
import com.dafttech.terra.world.entity.Player;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileStone;

public class Tile implements IRenderable {
    static Map<Integer, Class<?>> registry = new HashMap<Integer, Class<?>>();

    public static void registerTile(Integer id, Class<?> mat) {
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
        registerTile(2, TileStone.class);
    }

    Position position;
    TextureRegion image;
    List<Subtile> subtiles = new ArrayList<Subtile>();

    public Tile(Position pos, TextureRegion textureRegion) {
        position = pos;
        image = textureRegion;
    }

    public TileRenderer getRenderer() {
        return TileRendererBlock.$Instance;
    }

    public TextureRegion getImage() {
        return image;
    }

    public Position getPosition() {
        return position;
    }

    public void addSubtile(Subtile s) {
        s.setTile(this);
        subtiles.add(s);
    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        getRenderer().draw(screen, this, player);

        for (Subtile s : subtiles) {
            s.draw(screen, player);
        }
    }

    @Override
    public void update(Player player, float delta) {
        for (Subtile subtile : subtiles) {
            subtile.update(player, delta);
        }
    }
}

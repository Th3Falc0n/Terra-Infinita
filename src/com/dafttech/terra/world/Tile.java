package com.dafttech.terra.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.graphics.TileRenderer;
import com.dafttech.terra.graphics.renderers.TileRendererBlock;
import com.dafttech.terra.world.entities.Item;
import com.dafttech.terra.world.entities.Player;
import com.dafttech.terra.world.entities.items.ItemTile;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileGrass;
import com.dafttech.terra.world.tiles.TileStone;

public abstract class Tile implements IDrawable {
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
        registerTile(3, TileGrass.class);
    }

    Position position;
    List<Subtile> subtiles = new ArrayList<Subtile>();
    World world;

    public Tile(Position pos, World w) {
        position = pos;
        world = w;
    }
    
    public World getWorld() {
        return world;
    }
    
    public Item getItemDropped() {
        return new ItemTile(this);
    }

    public TileRenderer getRenderer() {
        return TileRendererBlock.$Instance;
    }

    public abstract TextureRegion getImage();

    public Position getPosition() {
        return position;
    }

    public float getWalkFriction() {
        return 1f;
    }

    public float getWalkAcceleration() {
        return 1f;
    }

    public Tile addSubtile(Subtile... subtile) {
        for (Subtile s : subtile) {
            if (s != null) {
                s.setTile(this);
                subtiles.add(s);
            }
        }
        return this;
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

    public boolean onCollisionWith(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }

    public void spawnAsEntity() {
        world.addEntity(getItemDropped());
    }
}

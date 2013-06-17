package com.dafttech.terra.world.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.graphics.renderers.TileRenderer;
import com.dafttech.terra.graphics.renderers.TileRendererBlock;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.entities.Player;
import com.dafttech.terra.world.entities.items.Item;
import com.dafttech.terra.world.entities.items.ItemTile;
import com.dafttech.terra.world.subtiles.Subtile;

public abstract class Tile implements IDrawable {
    static Map<Integer, Class<? extends Tile>> registry = new HashMap<Integer, Class<? extends Tile>>();

    public static void registerTile(Integer id, Class<? extends Tile> mat) {
        registry.put(id, mat);
    }

    public static Tile getInstanceOf(Integer id) {
        try {
            return registry.get(id).newInstance();
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

    public Position position = null;
    List<Subtile> subtiles = new ArrayList<Subtile>();
    public World world = null;

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

    public float getWalkFriction() {
        return 1f;
    }

    public void addedToWorld() {

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

    public Tile addToWorld(World world, Position pos) {
        if (position == null || world == null) {
            this.position = new Position(pos);
            this.world = world;
            world.map[pos.x][pos.y] = this;
            addedToWorld();
        }
        return this;
    }

    public boolean isLightEmitter() {
        return false;
    }

    public PointLight getEmittedLight() {
        return null;
    }

    public void spawnAsEntity() {
        world.addEntity(getItemDropped());
    }
}

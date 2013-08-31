package com.dafttech.terra.game.world;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawable;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class Chunk implements IDrawable {
    public World world;
    public Vector2 pos;
    public Tile[][] map;
    public List<Entity> localEntities = new CopyOnWriteArrayList<Entity>();

    public Chunk(World world, Vector2 pos) {
        this.world = world;
        this.pos = pos;
        this.map = new Tile[(int) world.chunksize.x][(int) world.chunksize.y];
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        // TODO Auto-generated method stub

    }

    public Chunk getChunk() {
        return null;
    }

    public Vector2 getChunkPos(Vector2 pos) {
        return new Vector2(pos.x % world.chunksize.x, pos.y % world.chunksize.y);
    }

    protected Tile getTile(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) return map[x][y];
        return null;
    }

    protected void setTile(Tile tile, Position pos) {
        // if (pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y)
        // map[pos.x][pos.y] = tile;
    }
}

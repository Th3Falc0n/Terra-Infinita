package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawable;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class Chunk implements IDrawable {
    public World world;
    public Vector2i pos;
    public Tile[][] map;
    public List<Entity> localEntities = new CopyOnWriteArrayList<Entity>();

    public Chunk(World world, Vector2i pos) {
        this.world = world;
        this.pos = pos;
        this.map = new Tile[(int) world.chunksize.x][(int) world.chunksize.y];
    }

    @Override
    public void update(float delta) {
        Tile tile;
        for (int y = 0; y < world.chunksize.y; y++) {
            for (int x = 0; x < world.chunksize.x; x++) {
                tile = getTile(x, y);
                if (tile != null) tile.update(delta);
            }
        }
        for (Entity entity : localEntities) {
            entity.update(delta);
            if (entity.getPosition().x < -100 || entity.getPosition().x > world.size.x * BLOCK_SIZE + 100
                    || entity.getPosition().y > world.size.y * BLOCK_SIZE + 100) {
                world.removeEntity(entity);
            }
        }
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        Tile tile;
        for (int y = 0; y < world.chunksize.y; y++) {
            for (int x = 0; x < world.chunksize.x; x++) {
                tile = getTile(x, y);
                if (tile != null) tile.draw(screen, pointOfView);
            }
        }
    }
    
    public static Chunk getChunk() {
        return null;
    }

    protected Tile getTile(int x, int y) {
        if (x >= 0 && x < world.chunksize.x && y >= 0 && y < world.chunksize.y && map[x][y] != null) return map[x][y];
        return null;
    }

    protected Chunk setTile(int x, int y, Tile tile) {
        if (x >= 0 && x < world.chunksize.x && y >= 0 && y < world.chunksize.y) {
            map[x][y] = tile;
            if (tile != null && (tile.world == null || tile.position == null)) {
                tile.world = world;
                tile.position = pos.getWorldPos(this);
            }
        }
        return this;
    }

    protected Tile getTile(Vector2i pos) {
        return getTile(pos.getX(), pos.getY());
    }

    protected Chunk setTile(Vector2i pos, Tile tile) {
        return setTile(pos.getX(), pos.getY(), tile);
    }
}

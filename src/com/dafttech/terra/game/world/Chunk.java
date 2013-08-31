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
        Vector2i pos = new Vector2i();
        for (pos.y = 0; pos.y < world.chunksize.y; pos.y++) {
            for (pos.x = 0; pos.x < world.chunksize.x; pos.x++) {
                tile = getTile(pos);
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
        Vector2i pos = new Vector2i();
        for (pos.y = 0; pos.y < world.chunksize.y; pos.y++) {
            for (pos.x = 0; pos.x < world.chunksize.x; pos.x++) {
                tile = getTile(pos);
                if (tile != null) tile.draw(screen, pointOfView);
            }
        }
    }

    public static Chunk getChunk(Vector2i blockInWorldPos) {
        return null;
    }

    protected Tile getTile(Vector2i blockInChunkPos) {
        if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) return map[blockInChunkPos.x][blockInChunkPos.y];
        return null;
    }

    public Chunk setTile(Vector2i blockInChunkPos, Tile tile) {
        if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) {
            if (tile != null) {
                tile.world = world;
                tile.position = blockInChunkPos.getBlockInWorldPos(this);
            }
            map[blockInChunkPos.x][blockInChunkPos.y] = tile;
        }
        return this;
    }
}

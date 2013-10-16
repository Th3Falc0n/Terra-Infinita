package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.passes.RenderingPass;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.tiles.ITileInworldEvents;
import com.dafttech.terra.game.world.tiles.Tile;

public class World implements IDrawableInWorld {
    public Vector2i size = new Vector2i(0, 0);
    public Vector2i chunksize = new Vector2i(32, 32);
    public WorldGenerator gen;
    private float tickProgress = 0, tickLength = 0.05f;

    public Map<Vector2i, Chunk> localChunks = new ConcurrentHashMap<Vector2i, Chunk>();

    public Player localPlayer = new Player(new Vector2(0, 0), this);

    public World(Vector2 size) {
        this.size.set((int) size.x, (int) size.y);
        gen = new WorldGenerator(this);
        // gen.generate();
        localPlayer.setPosition(new Vector2(0, -100));
    }

    public Chunk getChunk(Vector2i blockInWorldPos) {
        Vector2i chunkPos = blockInWorldPos.getChunkPos(this);
        if (localChunks.containsKey(chunkPos)) return localChunks.get(chunkPos);
        return null;
    }

    public Chunk getOrCreateChunk(Vector2i blockInWorldPos) {
        Chunk chunk = getChunk(blockInWorldPos);
        if (chunk == null) chunk = new Chunk(this, blockInWorldPos.getChunkPos(this));
        return chunk;
    }

    public Chunk getChunk(Vector2 blockInWorldPos) {
        if (blockInWorldPos == null) return null;
        Vector2 chunkPos = blockInWorldPos.getChunkPos(this);
        if (localChunks.containsKey(chunkPos)) return localChunks.get(chunkPos);
        return null;
    }

    public Chunk getOrCreateChunk(Vector2 blockInWorldPos) {
        if (blockInWorldPos == null) return null;
        Chunk chunk = getChunk(blockInWorldPos);
        if (chunk == null) chunk = new Chunk(this, blockInWorldPos.getChunkPos(this));
        return chunk;
    }

    public boolean doesChunkExist(int x, int y) {
        return getChunk(new Vector2i(x, y)) != null;
    }

    public Tile getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public Tile getTile(Vector2i pos) {
        Chunk chunk = getOrCreateChunk(pos);
        if (chunk != null) return chunk.get(pos.getBlockInChunkPos(this));
        return null;
    }

    public Tile getNextTileBelow(int x, int y) {
        y++;
        while (doesChunkExist(x, y)) {
            if (getTile(x, y) != null) return getTile(x, y);
            y++;
        }
        return null;
    }

    public Tile getNextTileBelow(Vector2i t) {
        return getNextTileBelow(t.x, t.y);
    }

    public Tile getNextTileAbove(int x, int y) {
        y--;
        while (doesChunkExist(x, y)) {
            if (getTile(x, y) != null) return getTile(x, y);
            y--;
        }
        return null;
    }

    public Tile getNextTileAbove(Vector2i t) {
        return getNextTileAbove(t.x, t.y);
    }

    public World setTile(Vector2i pos, Tile tile, boolean notify) {
        Chunk chunk = getOrCreateChunk(pos);
        if (chunk != null) {
            boolean oldTile = false;
            Vector2i oldPos = null;
            if (tile != null && tile.position != null && getTile(tile.position) == tile) {
                oldTile = true;
                oldPos = tile.position.clone();
            }
            chunk.set(pos.getBlockInChunkPos(this), tile);
            if (notify) {
                if (tile != null && tile instanceof ITileInworldEvents) ((ITileInworldEvents) tile).onTileSet();
                notifyNeighborTiles(pos.x, pos.y);
            }
            if (oldTile) setTile(oldPos, null, notify);
        }
        return this;
    }

    public World setTile(int x, int y, Tile tile, boolean notify) {
        return setTile(new Vector2i(x, y), tile, notify);
    }

    public void destroyTile(int x, int y, Entity causer) {
        Tile tile = getTile(x, y);
        if (tile != null) {
            tile.spawnAsEntity();
            setTile(x, y, null, true);

            if (tile instanceof ITileInworldEvents) {
                ((ITileInworldEvents) tile).onTileDestroyed(causer);
            }
        }
    }

    public void placeTile(int x, int y, Tile t, Entity causer) {
        Tile tile = getTile(x, y);
        if (tile == null) {
            setTile(x, y, t, true);

            if (tile instanceof ITileInworldEvents) {
                ((ITileInworldEvents) tile).onTilePlaced(causer);
            }
        }
    }

    private void notifyNeighborTiles(int x, int y) {
        Tile tile;
        tile = getTile(x + 1, y);
        if (tile != null && tile instanceof ITileInworldEvents) ((ITileInworldEvents) tile).onNeighborChange(tile);
        tile = getTile(x, y + 1);
        if (tile != null && tile instanceof ITileInworldEvents) ((ITileInworldEvents) tile).onNeighborChange(tile);
        tile = getTile(x - 1, y);
        if (tile != null && tile instanceof ITileInworldEvents) ((ITileInworldEvents) tile).onNeighborChange(tile);
        tile = getTile(x, y - 1);
        if (tile != null && tile instanceof ITileInworldEvents) ((ITileInworldEvents) tile).onNeighborChange(tile);
    }

    public void addEntity(Entity entity) {
        // localEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        // localEntities.remove(entity);
    }

    @Override
    public void update(float delta) {
        int sx = 25 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 25 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        Tile tile;
        for (int x = (int) localPlayer.getPosition().x / BLOCK_SIZE - sx; x < (int) localPlayer.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) localPlayer.getPosition().y / BLOCK_SIZE - sy; y < (int) localPlayer.getPosition().y / BLOCK_SIZE + sy; y++) {
                tile = getTile(x, y);
                if (tile != null) tile.update(delta);
            }
        }

        for (Entity entity : getLocalEntities()) {
            entity.update(delta);
        }
        tickProgress += delta;
        if (tickProgress >= tickLength) {
            tickProgress -= tickLength;
            Events.EVENT_WORLDTICK.callAsync(this);
        }
    }

    public boolean isInRenderRange(Vector2 position) {
        return true;
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        RenderingPass.rpObjects.applyPass(screen, pointOfView, this);
        RenderingPass.rpLighting.applyPass(screen, pointOfView, this);
    }

    public List<Entity> getLocalEntities() {
        List<Entity> localEntities = new ArrayList<Entity>();
        for (Chunk chunk : localChunks.values()) {
            localEntities.addAll(chunk.getLocalEntities());
        }
        return localEntities;
    }
}

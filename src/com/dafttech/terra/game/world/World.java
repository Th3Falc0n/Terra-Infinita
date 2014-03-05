package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.passes.RenderingPass;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.TimeKeeping;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.environment.SunMap;
import com.dafttech.terra.game.world.environment.Weather;
import com.dafttech.terra.game.world.environment.WeatherRainy;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.subtiles.Subtile;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileAir;

public class World implements IDrawableInWorld {
    public Vector2i size = new Vector2i(0, 0);
    public Vector2i chunksize = new Vector2i(32, 32);
    public float time = 0, lastTick = 0, tickLength = 0.005f;
    public WorldGenerator gen;

    public Map<Vector2i, Chunk> localChunks = new ConcurrentHashMap<Vector2i, Chunk>();

    public Player localPlayer;

    public Weather weather = new WeatherRainy();

    public SunMap sunmap = new SunMap();

    public World(Vector2 size) {
        this.size.set((int) size.x, (int) size.y);

        gen = new WorldGenerator(this);

        localPlayer = new Player(new Vector2(), this);
        localPlayer.setPosition(new Vector2(0, -100));
    }

    public Chunk getChunk(Vector2i blockInWorldPos) {
        if (blockInWorldPos == null) return null;
        Vector2i chunkPos = blockInWorldPos.getChunkPos(this);
        if (localChunks.containsKey(chunkPos)) return localChunks.get(chunkPos);
        return null;
    }

    public Chunk getOrCreateChunk(Vector2i blockInWorldPos) {
        Chunk chunk = getChunk(blockInWorldPos);
        if (chunk == null) {
            chunk = new Chunk(this, blockInWorldPos.getChunkPos(this));
            localChunks.put(blockInWorldPos.getChunkPos(this), chunk);
            chunk.fillAir();

            gen.generateChunk(chunk);
        }
        return chunk;
    }

    public Chunk getChunk(Vector2 blockInWorldPos) {
        if (blockInWorldPos == null) return null;
        return getChunk(blockInWorldPos.toVector2i());
    }

    public Chunk getOrCreateChunk(Vector2 blockInWorldPos) {
        if (blockInWorldPos == null) return null;
        return getOrCreateChunk(blockInWorldPos.toVector2i());
    }

    public boolean doesChunkExist(int x, int y) {
        return getChunk(new Vector2i(x, y)) != null;
    }

    public Tile getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public Tile getTile(Vector2i pos) {
        Chunk chunk = getOrCreateChunk(pos);
        if (chunk != null) return chunk.getTile(pos.getBlockInChunkPos(this));
        return null;
    }

    public Tile getNextTileBelow(int x, int y) {
        y++;
        while (doesChunkExist(x, y)) {
            if (getTile(x, y) != null && !getTile(x, y).isAir()) return getTile(x, y);
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
            if (getTile(x, y) != null && !getTile(x, y).isAir()) return getTile(x, y);
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
            List<Subtile> tileIndependentSubtiles = new ArrayList<Subtile>();
            Vector2i notifyOldRemoval = null;
            if (tile != null && tile.getPosition() != null && getTile(tile.getPosition()) == tile) {
                notifyOldRemoval = tile.getPosition();
                setTile(notifyOldRemoval, null, false);
            }
            if (tile == null) tile = new TileAir();

            tile.setPosition(pos).setWorld(this);

            Tile oldTile = getTile(pos);
            if (oldTile != null) {
                sunmap.postTileRemove(this, oldTile);
                for (Subtile subtile : oldTile.getSubtiles()) {
                    if (subtile.isTileIndependent()) tileIndependentSubtiles.add(subtile);
                }
                oldTile.removeAndUnlinkSubtile(tileIndependentSubtiles.toArray(new Subtile[0]));
            }

            chunk.setTile(pos.getBlockInChunkPos(this), tile);

            tile.addSubtile(tileIndependentSubtiles.toArray(new Subtile[0]));

            sunmap.postTilePlace(this, tile);

            if (notify) {
                tile.onTileSet(this);
                if (notifyOldRemoval != null) notifyNeighborTiles(notifyOldRemoval.x, notifyOldRemoval.y);
                notifyNeighborTiles(pos.x, pos.y);
            }
        }
        return this;
    }

    public World setTile(int x, int y, Tile tile, boolean notify) {
        return setTile(new Vector2i(x, y), tile, notify);
    }

    public boolean placeTile(int x, int y, Tile t, Entity causer) {
        Tile tile = getTile(x, y);
        if (tile.isAir() || tile.isReplacable()) {
            setTile(x, y, t, true);

            tile.onTilePlaced(this, causer);
            return true;
        }
        return false;
    }

    public EntityItem destroyTile(int x, int y, Entity causer) {
        EntityItem entity = null;
        Tile tile = getTile(x, y);
        if (tile.isBreakable()) {
            entity = tile.spawnAsEntity(this);
            setTile(x, y, null, true);

            tile.onTileDestroyed(this, causer);
        }
        return entity;
    }

    private void notifyNeighborTiles(int x, int y) {
        Tile tile;
        tile = getTile(x + 1, y);
        tile.onNeighborChange(this, tile);
        tile = getTile(x, y + 1);
        tile.onNeighborChange(this, tile);
        tile = getTile(x - 1, y);
        tile.onNeighborChange(this, tile);
        tile = getTile(x, y - 1);
        tile.onNeighborChange(this, tile);
    }

    public void removeEntity(Entity entity) {
        entity.remove();
    }

    @Override
    public void update(World world, float delta) {
        time += delta;

        weather.update(this, delta);

        int sx = 25 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 25 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        Tile tile;
        for (int x = (int) localPlayer.getPosition().x / BLOCK_SIZE - sx; x < (int) localPlayer.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) localPlayer.getPosition().y / BLOCK_SIZE - sy; y < (int) localPlayer.getPosition().y / BLOCK_SIZE + sy; y++) {
                tile = getTile(x, y);
                if (tile != null) tile.update(this, delta);
            }
        }

        TimeKeeping.timeKeeping("Tile update");

        for (Chunk chunk : localChunks.values()) {
            for (Entity entity : chunk.getLocalEntities()) {
                entity.update(this, delta);
            }
        }

        TimeKeeping.timeKeeping("Entity update");

        Events.EVENTMANAGER.callSync(Events.EVENT_WORLDTICK, this, time - lastTick);
        lastTick = time;

        TimeKeeping.timeKeeping("Tick update");
    }

    public boolean isInRenderRange(Vector2 position) {
        return true;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        RenderingPass.rpObjects.applyPass(screen, pointOfView, this);
        TimeKeeping.timeKeeping("rpObj");
        RenderingPass.rpLighting.applyPass(screen, pointOfView, this);
        TimeKeeping.timeKeeping("rpLig");
    }
}

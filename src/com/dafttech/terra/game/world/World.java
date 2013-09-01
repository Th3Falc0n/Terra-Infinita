package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawable;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.passes.RenderingPass;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.tiles.ITileInworldEvents;
import com.dafttech.terra.game.world.tiles.Tile;

public class World implements IDrawable {
    public Vector2i size = new Vector2i(0, 0);
    public Vector2i chunksize = new Vector2i(32, 32);
    public WorldGenerator gen;
    public List<Entity> localEntities = new CopyOnWriteArrayList<Entity>();

    public Map<Vector2i, Chunk> localChunks = new ConcurrentHashMap<Vector2i, Chunk>();

    public Player localPlayer = new Player(new Vector2(0, 0), this);

    public World(Vector2 size) {
        this.size.set((int) size.x, (int) size.y);
        gen = new WorldGenerator(this);
        // gen.generate();
        localPlayer.setPosition(new Vector2(0, -100));
    }

    public Tile getTile(Vector2i pos) {
        Chunk chunk = Chunk.getOrCreateChunk(this, pos);
        if (chunk != null) return chunk.getTile(pos.getBlockInChunkPos(this));
        return null;
    }

    public Tile getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public World setTile(Vector2i pos, Tile tile) {
        Chunk chunk = Chunk.getOrCreateChunk(this, pos);
        if (chunk != null) chunk.setTile(pos.getBlockInChunkPos(this), tile);
        return this;
    }

    public World setTile(int x, int y, Tile tile) {
        return setTile(new Vector2i(x, y), tile);
    }

    public void destroyTile(int x, int y, Entity causer) {
        Tile tile = getTile(x, y);
        if (tile != null) {
            tile.spawnAsEntity();
            setTile(x, y, null);

            if (tile instanceof ITileInworldEvents) {
                ((ITileInworldEvents) tile).onTileDestroyed(causer);
            }
            notifyNeighborTiles(x, y);
        }
    }

    public void placeTile(int x, int y, Tile t, Entity causer) {
        Tile tile = getTile(x, y);
        if (tile == null) {
            setTile(x, y, t);

            if (tile instanceof ITileInworldEvents) {
                ((ITileInworldEvents) tile).onTilePlaced(causer);
            }
            notifyNeighborTiles(x, y);
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
        localEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        localEntities.remove(entity);
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

        for (Entity entity : localEntities) {
            entity.update(delta);
        }

        Events.EVENT_WORLDTICK.callSync(this);
    }

    public boolean isInRenderRange(Vector2 position) {
        return true;
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        RenderingPass.rpObjects.applyPass(screen, pointOfView, this);
        RenderingPass.rpLighting.applyPass(screen, pointOfView, this);
    }
}

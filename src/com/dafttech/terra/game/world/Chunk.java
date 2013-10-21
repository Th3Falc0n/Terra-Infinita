package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.gen.biomes.Biome;
import com.dafttech.terra.game.world.gen.biomes.BiomeDesert;
import com.dafttech.terra.game.world.gen.biomes.BiomeGrassland;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileAir;

public class Chunk implements IDrawableInWorld {
    public World world;
    public Vector2i pos;
    public Tile[][] map;
    private List<Entity> localEntities = new ArrayList<Entity>();
    public boolean stayLoaded = false;
    private boolean regenerate = false;

    public Chunk(World world, Vector2i chunkPos) {
        this.world = world;
        this.pos = chunkPos;
        this.map = new Tile[world.chunksize.x][world.chunksize.y];
        Vector2i airPos = new Vector2i();
        for (airPos.y = 0; airPos.y < map[0].length; airPos.y++) {
            for (airPos.x = 0; airPos.x < map.length; airPos.x++) {
                setTile(airPos, new TileAir());
            }
        }
        world.localChunks.put(chunkPos, this);
        regenerate();
    }

    public Chunk(World world, Vector2 chunkPos) {
        this(world, new Vector2i(chunkPos));
    }

    @Deprecated
    @Override
    public void update(World world, float delta) {
        Tile tile;
        Vector2i pos = new Vector2i();
        for (pos.y = 0; pos.y < world.chunksize.y; pos.y++) {
            for (pos.x = 0; pos.x < world.chunksize.x; pos.x++) {
                tile = getTile(pos);
                if (tile != null) tile.update(world, delta);
            }
        }

        for (Entity entity : localEntities) {
            entity.update(world, delta);
            if (entity.getPosition().x < -100 || entity.getPosition().x > world.size.x * BLOCK_SIZE + 100
                    || entity.getPosition().y > world.size.y * BLOCK_SIZE + 100) {
                world.removeEntity(entity);
            }
        }
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        Tile tile;
        Vector2i tilePos = new Vector2i();
        for (tilePos.y = 0; tilePos.y < world.chunksize.y; tilePos.y++) {
            for (tilePos.x = 0; tilePos.x < world.chunksize.x; tilePos.x++) {
                tile = getTile(tilePos);
                if (tile != null) tile.draw(pos, world, screen, pointOfView);
            }
        }
    }

    public Biome getBiome() {
        return new Random().nextBoolean() ? BiomeGrassland.instance : BiomeDesert.instance;
    }

    public void regenerate() {
        regenerate = true;
    }

    private void tryRegenerate() {
        regenerate = false;
        world.gen.generateChunk(this);
    }

    protected Tile getTile(Vector2i blockInChunkPos) {
        if (regenerate) tryRegenerate();
        if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) {
            if (map[blockInChunkPos.x][blockInChunkPos.y] == null) setTile(blockInChunkPos, new TileAir());
            return map[blockInChunkPos.x][blockInChunkPos.y];
        }
        return null;
    }

    public Chunk setTile(Vector2i blockInChunkPos, Tile tile) {
        if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) {
            if (tile == null) tile = new TileAir();
            tile.setPosition(blockInChunkPos.getBlockInWorldPos(this));
            if (!Events.EVENT_BLOCKCHANGE.callSync(tile).isCancelled()) map[blockInChunkPos.x][blockInChunkPos.y] = tile;
        }
        return this;
    }

    public boolean removeEntity(Entity entity) {
        return localEntities.remove(entity);
    }

    public boolean addEntity(Entity entity) {
        return localEntities.add(entity);
    }

    public Entity[] getLocalEntities() {
        return localEntities.toArray(new Entity[localEntities.size()]);
    }
}

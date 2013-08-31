package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.List;
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
import com.dafttech.terra.game.world.tiles.Tile;

public class World implements IDrawable {
    public Vector2i size = new Vector2i(0, 0);
    public Vector2i chunksize = new Vector2i(16, 16);
    public WorldGenerator gen;
    public List<Entity> localEntities = new CopyOnWriteArrayList<Entity>();

    public List<Chunk> localChunks = new CopyOnWriteArrayList<Chunk>();

    public Player localPlayer = new Player(new Vector2(0, 0), this);

    public World(Vector2 size) {
        this.size.set((int) size.x, (int) size.y);
        gen = new WorldGenerator(this);
        gen.generate();
        localPlayer.setPosition(new Vector2(0, -100));
    }

    public Tile getTile(int x, int y) {
        Vector2i pos = new Vector2i(x, y);
        Chunk chunk = pos.getChunk(this);
        if (chunk != null) return chunk.getTile(pos.getBlockInChunkPos(this));
        return null;
    }

    public void setTile(int x, int y, Tile tile) {
        Vector2i pos = new Vector2i(x, y);
        Chunk chunk = pos.getOrCreateChunk(this);
        if (chunk != null) chunk.setTile(pos.getBlockInChunkPos(this), tile);
    }

    public void destroyTile(int x, int y) {
        Tile tile = getTile(x, y);
        if (tile != null) {
            tile.spawnAsEntity();
            setTile(x, y, null);
        }
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

        for (Chunk chunk : localChunks) {
            chunk.update(delta);
        }

        for (Entity entity : localEntities) {
            entity.update(delta);

            if (entity.getPosition().x < -100 || entity.getPosition().x > size.getX() * BLOCK_SIZE + 100
                    || entity.getPosition().y > size.getY() * BLOCK_SIZE + 100) {
                removeEntity(entity);
            }
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

package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.gen.WorldGenerator;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.graphics.passes.RenderingPass;

public class World implements IDrawable {
    public Vector2 size = new Vector2(0, 0);
    public Tile[][] map;
    public WorldGenerator gen;
    public List<Entity> localEntities = new CopyOnWriteArrayList<Entity>();

    public Queue<IDrawable> renderJobs = new ConcurrentLinkedQueue<IDrawable>();

    public Player localPlayer = new Player(new Vector2(0, 0), this);

    World physWorld;

    public World(Vector2 size) {
        this.size.x = (int) size.x;
        this.size.y = (int) size.y;
        map = new Tile[(int) size.x][(int) size.y];
        gen = new WorldGenerator(this);
        gen.generate();
        localPlayer.setPosition(new Vector2(0, -100));
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) return map[x][y];
        return null;
    }

    public void setTile(Tile tile, Position pos) {
        if (pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y) map[pos.x][pos.y] = tile;
    }

    public void destroyTile(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) {
            map[x][y].spawnAsEntity();
            map[x][y] = null;
        }
    }

    public void addEntity(Entity entity) {
        localEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        localEntities.remove(entity);
    }

    public void enqueueForRender(int x, int y) {
        if (map[x][y] != null) {
            renderJobs.add(map[x][y]);
        }
    }

    @Override
    public void update(float delta) {
        int sx = 25 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 25 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) localPlayer.getPosition().x / BLOCK_SIZE - sx; x < (int) localPlayer.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) localPlayer.getPosition().y / BLOCK_SIZE - sy; y < (int) localPlayer.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) {
                    map[x][y].update(delta);

                    if (map[x][y].isLightEmitter()) {
                        RenderingPass.rpLighting.addLight(map[x][y].getEmittedLight());
                    }
                }
            }
        }

        for (Entity entity : localEntities) {
            entity.update(delta);

            if (entity.getPosition().x < -100 || entity.getPosition().x > size.x * BLOCK_SIZE + 100
                    || entity.getPosition().y > size.y * BLOCK_SIZE + 100) {
                removeEntity(entity);
            }

            if (entity.isLightEmitter() && isInRenderRange(entity.getPosition())) {
                RenderingPass.rpLighting.addLight(entity.getEmittedLight());
            }
        }

        Events.EVENT_WORLDTICK.callSync(this);
    }

    public boolean isInRenderRange(Vector2 position) {
        return false;
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        RenderingPass.rpObjects.applyPass(screen, pointOfView, this);
        RenderingPass.rpLighting.applyPass(screen, pointOfView, this);
    }
}

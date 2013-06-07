package com.dafttech.terra.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.dafttech.terra.event.Events;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.entities.Player;
import com.dafttech.terra.world.gen.WorldGenerator;
import com.dafttech.terra.world.tiles.Tile;

public class World implements IDrawable {
    public Vector2 size = new Vector2(0, 0);
    public Tile[][] map;
    public WorldGenerator gen;
    public List<Entity> localEntities = new CopyOnWriteArrayList<Entity>();

    Pixmap lightMap;

    public Player localPlayer = new Player(new Vector2(0, 0), this);

    public World(Vector2 size) {
        this.size.x = (int) size.x;
        this.size.y = (int) size.y;
        map = new Tile[(int) size.x][(int) size.y];
        gen = new WorldGenerator(this);
        gen.generate();
        localPlayer.setPosition(new Vector2(0, map[0].length * BLOCK_SIZE));

        initLightmap();
    }

    public void initLightmap() {
        lightMap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGBA8888);
        lightMap.setColor(Color.RED);
        lightMap.fill();
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) return map[x][y];
        return null;
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

    @Override
    public void update(Player player, float delta) {
        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) player.getPosition().x / BLOCK_SIZE - sx; x < (int) player.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) player.getPosition().y / BLOCK_SIZE - sy; y < (int) player.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) map[x][y].update(player, delta);
            }
        }

        for (Entity entity : localEntities) {
            entity.update(player, delta);
        }

        Events.EVENT_WORLDTICK.callSync(this);
    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        // TODO: draw world

        screen.batch.enableBlending();
        screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        screen.batch.begin();

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) player.getPosition().x / BLOCK_SIZE - sx; x < (int) player.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) player.getPosition().y / BLOCK_SIZE - sy; y < (int) player.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) map[x][y].draw(screen, player);
            }
        }

        for (Entity entity : localEntities) {
            entity.draw(screen, player);
        }

        screen.batch.end();

        screen.batch.setBlendFunction(GL11.GL_DST_COLOR, GL11.GL_ZERO);

        Texture reg = new Texture(lightMap);

        screen.batch.begin();
        screen.batch.draw(reg, 0, 0);
        screen.batch.end();

        reg.dispose();

        localPlayer.drawCollisionBoxes(this);
    }
}

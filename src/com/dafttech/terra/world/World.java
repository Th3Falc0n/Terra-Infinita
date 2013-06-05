package com.dafttech.terra.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.event.Events;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IRenderable;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.world.entity.Entity;
import com.dafttech.terra.world.entity.Player;
import com.dafttech.terra.world.subtiles.SubtileGrass;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileStone;

public class World implements IRenderable {
    Tile[][] map;
    public List<Entity> localEntities = new ArrayList<Entity>();
    public Player localPlayer = new Player(new Vector2(0, 0), this);

    public World(int width, int height) {
        map = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = y > height - height / 6 ? new TileDirt(new Position(x, y)).addSubtile(y == height - 1 ? new SubtileGrass() : null)
                        : new TileStone(new Position(x, y));
            }
        }

        localPlayer.setPosition(new Vector2(250, map[0].length * BLOCK_SIZE + 250));
        //localEntities.add(localPlayer);

        // map[2][2].addSubtile(new SubtileGrass());
    }
    
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) return map[x][y];
        return null;
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

        screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        screen.batch.enableBlending();

        screen.batch.begin();

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) player.getPosition().x / BLOCK_SIZE - sx; x < (int) player.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) player.getPosition().y / BLOCK_SIZE - sy; y < (int) player.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != null) map[x][y].draw(screen, player);
            }
        }

        screen.batch.end();
    }
}

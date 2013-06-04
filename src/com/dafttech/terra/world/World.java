package com.dafttech.terra.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.entity.Entity;
import com.dafttech.terra.entity.Player;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.tiles.TileDirt;
import com.dafttech.terra.world.tiles.TileGrass;

public class World {
    Tile[][] map;
    List<Entity> entities = new ArrayList<Entity>();
    Player currentPlayer = null;

    public World(int width, int height) {
        map = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = y == 0 ? new TileGrass(new Position(x, y)) : new TileDirt(new Position(x, y));
            }
        }
    }

    public void drawWorld(AbstractScreen screen, Player player) {
        // TODO: draw world
        screen.batch.begin();

        int sx = Gdx.graphics.getWidth() / 8 / 2;
        int sy = Gdx.graphics.getHeight() / 8 / 2;

        for (int x = (int) player.getPosition().x - sx; x < (int) player.getPosition().x + sx; x++) {
            for (int y = (int) player.getPosition().y - sy; y < (int) player.getPosition().y + sy; y++) {
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) map[x][y].draw(screen, player);
            }
        }

        screen.batch.end();
    }
}

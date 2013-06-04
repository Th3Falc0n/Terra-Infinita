package com.dafttech.wai.world;

import com.badlogic.gdx.Gdx;
import com.dafttech.wai.graphics.AbstractScreen;
import com.dafttech.wai.world.materials.TileDirt;

public class World {
    Tile[][] map;

    public World(int width, int height) {
        map = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = new TileDirt(new Position(x, y));
            }
        }
    }

    public void drawWorld(AbstractScreen screen, Player player) {
        // TODO: draw world
        screen.batch.begin();

        int sx = (Gdx.graphics.getWidth() / 8) / 2;
        int sy = (Gdx.graphics.getHeight() / 8) / 2;

        for (int x = (int) player.getPosition().x - sx; x < (int) player.getPosition().x + sx; x++) {
            for (int y = (int) player.getPosition().y - sy; y < (int) player.getPosition().y + sy; y++) {
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) Renderer.drawTile(screen, map[x][y], player);
            }
        }

        screen.batch.end();
    }
}

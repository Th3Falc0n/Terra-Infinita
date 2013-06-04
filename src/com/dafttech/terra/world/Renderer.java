package com.dafttech.terra.world;

import com.dafttech.terra.graphics.AbstractScreen;

public abstract class Renderer {
    public abstract void draw(AbstractScreen screen, Tile tile, Player player);

    public static void drawTile(AbstractScreen screen, Tile tile, Player player) {
        tile.getRenderer().draw(screen, tile, player);
    }
}

package com.dafttech.terra.graphics.renderers;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.TileRenderer;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entity.Player;

public class TileRendererBlock extends TileRenderer {
    public static TileRenderer $Instance = new TileRendererBlock();

    @Override
    public void draw(AbstractScreen screen, Tile tile, Player player) {
        Vector2 screenVec = tile.getPosition().toScreenPos(player);

        screen.batch.draw(tile.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE, BLOCK_SIZE);
    }
}

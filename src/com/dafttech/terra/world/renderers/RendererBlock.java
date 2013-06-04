package com.dafttech.terra.world.renderers;

import com.dafttech.terra.entity.Player;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Renderer;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.Vector2;

public class RendererBlock extends Renderer {
    public static Renderer $Instance = new RendererBlock();

    @Override
    public void draw(AbstractScreen screen, Tile tile, Player player) {
        Vector2 screenVec = tile.getPosition().toScreenPos(player);

        screen.batch.draw(tile.getImage(), screenVec.x, screenVec.y, 8, 8);
    }
}

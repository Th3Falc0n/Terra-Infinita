package com.dafttech.terra.graphics.renderers;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.SubtileRenderer;
import com.dafttech.terra.world.Subtile;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entity.Player;

import static com.dafttech.terra.resources.Options.*;

public class SubtileRendererMask extends SubtileRenderer {
    public static SubtileRenderer $Instance = new SubtileRendererMask();

    @Override
    public void draw(AbstractScreen screen, Subtile render, Player player) {
        Vector2 screenVec = render.getTile().getPosition().toScreenPos(player);

        screen.batch.draw(render.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE, BLOCK_SIZE);
    }
}

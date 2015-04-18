package com.dafttech.terra.engine.renderer;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.subtiles.Subtile;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

public class SubtileRendererMask extends SubtileRenderer {
    public static SubtileRenderer $Instance = new SubtileRendererMask();

    @Override
    public void draw(AbstractScreen screen, Subtile render, Entity pointOfView, Object... rendererArguments) {
        Vector2 screenVec = render.getTile().getPosition().toScreenPos(pointOfView);

        float rotation = rendererArguments.length > 0 ? (float) rendererArguments[0] : 0;

        float offX = 0, offY = 0;
        if (!render.isTileIndependent() && render.getTile() != null) {
            Vector2 offset = render.getTile().getRenderer().getOffset();
            if (offset != null) {
                offX = offset.x * BLOCK_SIZE;
                offY = offset.y * BLOCK_SIZE;
            }
        }

        screen.batch.draw(render.getImage(), screenVec.x + offX, screenVec.y + offY, 1, 1, BLOCK_SIZE, BLOCK_SIZE, 1, 1, rotation);
    }
}

package com.dafttech.terra.engine.renderer;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.ITileRenderOffset;
import com.dafttech.terra.game.world.tiles.Tile;

public class TileRendererBlock extends TileRenderer {
    public static TileRenderer $Instance = new TileRendererBlock();

    @Override
    public void draw(AbstractScreen screen, Tile tile, Entity pointOfView, Object... rendererArguments) {
        Vector2 screenVec = tile.getPosition().toScreenPos(pointOfView);

        float offX = 0, offY = 0;
        if (tile instanceof ITileRenderOffset) {
            Vector2 offset = ((ITileRenderOffset) tile).getRenderOffset();
            if (offset != null) {
                offX = offset.x;
                offY = offset.y;
            }
        }
        screen.batch.draw(tile.getImage(), screenVec.x + offX * BLOCK_SIZE, screenVec.y + offY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }
}

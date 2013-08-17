package com.dafttech.terra.engine.renderer;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class TileRendererBlock extends TileRenderer {
    public static TileRenderer $Instance = new TileRendererBlock();

    @Override
    public void draw(AbstractScreen screen, Tile tile, Entity pointOfView, Object... rendererArguments) {
        Vector2 screenVec = tile.position.toScreenPos(pointOfView);

        screen.batch.draw(tile.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE, BLOCK_SIZE);
    }
}

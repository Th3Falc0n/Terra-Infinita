package com.dafttech.terra.engine.renderer;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class TileRendererBlock extends TileRenderer {
    public static TileRenderer $Instance = new TileRendererBlock();

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Tile tile, Entity pointOfView, Object... rendererArguments) {
        Vector2 screenVec = tile.getPosition().toScreenPos(pointOfView);
        screen.batch.draw(tile.getImage(), screenVec.x + offset.x * BLOCK_SIZE, screenVec.y + offset.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }
}

package com.dafttech.terra.engine.renderer;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.subtiles.Subtile;
import com.dafttech.terra.game.world.subtiles.SubtileFluid;

public class SubtileRendererFluid extends SubtileRendererMask {
    public static SubtileRenderer $Instance = new SubtileRendererFluid();

    @Override
    public void draw(AbstractScreen screen, Subtile render, Entity pointOfView, Object... rendererArguments) {
        Vector2 screenVec = render.getTile().getPosition().toScreenPos(pointOfView);

        float rotation = rendererArguments.length > 0 ? (float) rendererArguments[0] : 0;

        float height = ((SubtileFluid) render).pressure / ((SubtileFluid) render).maxPressure * BLOCK_SIZE;
        if (height < BLOCK_SIZE && ((SubtileFluid) render).isFluid(render.getTile().getWorld(), Facing.TOP)) {
            SubtileFluid above = ((SubtileFluid) render).getFluid(render.getTile().getWorld(), Facing.TOP);
            if (above.pressure > above.maxPressure / 10) {
                height = BLOCK_SIZE;
            }
        }
        if (height > BLOCK_SIZE) height = BLOCK_SIZE;
        screen.batch.draw(render.getImage(), screenVec.x, screenVec.y + (BLOCK_SIZE - height), 1, 1, BLOCK_SIZE, height, 1, 1, rotation);
    }
}

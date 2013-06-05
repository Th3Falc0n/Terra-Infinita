package com.dafttech.terra.graphics.renderers;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.SubtileRenderer;
import com.dafttech.terra.world.Subtile;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entity.Player;

public class SubtileRendererMask extends SubtileRenderer {
    public static SubtileRenderer $Instance = new SubtileRendererMask();

    @Override
    public void draw(AbstractScreen screen, Subtile render, Player player, Object... rendererArguments) {
        Vector2 screenVec = render.getTile().position.toScreenPos(player);

        float rotation = rendererArguments.length > 0 ? (float) rendererArguments[0] : 0;

        screen.batch.draw(render.getImage(), screenVec.x, screenVec.y, 1, 1, BLOCK_SIZE, BLOCK_SIZE, 1, 1, rotation);
    }
}

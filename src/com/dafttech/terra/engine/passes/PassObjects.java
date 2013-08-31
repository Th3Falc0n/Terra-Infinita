package com.dafttech.terra.engine.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class PassObjects extends RenderingPass {

    @Override
    public void applyPass(AbstractScreen screen, Entity pointOfView, World world, Object... arguments) {
        screen.batch.setShader(null);

        screen.batch.enableBlending();
        screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        screen.batch.begin();

        Tile tile;
        for (int x = (int) pointOfView.getPosition().x / BLOCK_SIZE - sx; x < (int) pointOfView.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) pointOfView.getPosition().y / BLOCK_SIZE - sy; y < (int) pointOfView.getPosition().y / BLOCK_SIZE + sy; y++) {
                tile = world.getTile(x, y);
                if (tile != null) tile.draw(screen, pointOfView);
            }
        }

        for (Entity entity : world.localEntities) {
            entity.draw(screen, pointOfView);
        }

        screen.batch.end();
    }

}

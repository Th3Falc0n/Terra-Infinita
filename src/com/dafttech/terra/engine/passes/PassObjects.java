package com.dafttech.terra.engine.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public class PassObjects extends RenderingPass {

    @Override
    public void applyPass(AbstractScreen screen, Entity pointOfView, World w, Object... arguments) {
        screen.batch.setShader(null);

        screen.batch.enableBlending();
        screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        screen.batch.begin();

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) pointOfView.getPosition().x / BLOCK_SIZE - sx; x < (int) pointOfView.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) pointOfView.getPosition().y / BLOCK_SIZE - sy; y < (int) pointOfView.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (x >= 0 && x < w.map.length && y >= 0 && y < w.map[0].length && w.map[x][y] != null) w.map[x][y].draw(screen, pointOfView);
            }
        }

        for (Entity entity : w.localEntities) {
            entity.draw(screen, pointOfView);
        }

        screen.batch.end();
    }

}

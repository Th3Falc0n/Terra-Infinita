package com.dafttech.terra.graphics.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.entities.Player;

public class PassObjects extends RenderingPass {
    FrameBuffer buffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    @Override
    public void applyPass(AbstractScreen screen, Player player, World w, Object... arguments) {
        buffer.begin();
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        screen.batch.setShader(null);

        screen.batch.enableBlending();
        screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        screen.batch.begin();

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) player.getPosition().x / BLOCK_SIZE - sx; x < (int) player.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) player.getPosition().y / BLOCK_SIZE - sy; y < (int) player.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (x >= 0 && x < w.map.length && y >= 0 && y < w.map[0].length && w.map[x][y] != null) w.map[x][y].draw(screen, player);
            }
        }

        for (Entity entity : w.localEntities) {
            entity.draw(screen, player);
        }

        screen.batch.end();

        buffer.end();

        screen.batch.setShader(null);

        screen.batch.begin();
        screen.batch.draw(buffer.getColorBufferTexture(), 0, 0);
        screen.batch.end();
    }

}

package com.dafttech.terra.engine.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public class PassLighting extends RenderingPass {
    FrameBuffer buffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    @Override
    public void applyPass(AbstractScreen screen, Entity pointOfView, World world, Object... arguments) {
        buffer.begin();

        Gdx.graphics.getGL20().glClearColor(.15f, .15f, .15f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        screen.batch.enableBlending();
        screen.batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) pointOfView.getPosition().x / BLOCK_SIZE - sx; x < (int) pointOfView.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) pointOfView.getPosition().y / BLOCK_SIZE - sy; y < (int) pointOfView.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (world.getTile(x, y) != null) {
                    
                    
                    if (world.getTile(x, y).isLightEmitter() && world.getTile(x, y).getEmittedLight() != null) {
                        world.getTile(x, y).getEmittedLight().drawToLightmap(screen, pointOfView);
                    }
                }
            }
        }

        for (Entity entity : world.localEntities) {
            if (entity.isLightEmitter() && entity.getEmittedLight() != null && world.isInRenderRange(entity.getPosition())) {
                entity.getEmittedLight().drawToLightmap(screen, pointOfView);
            }
        }

        screen.batch.setColor(Color.WHITE);

        buffer.end();

        RenderingPass.rpGaussian.applyPass(screen, world.localPlayer, world, buffer.getColorBufferTexture(), buffer);

        screen.batch.setShader(null);
        screen.batch.setBlendFunction(GL10.GL_DST_COLOR, GL10.GL_ZERO);
        screen.batch.enableBlending();

        screen.batch.begin();
        screen.batch.draw(buffer.getColorBufferTexture(), 0, 0);
        screen.batch.end();
    }

}

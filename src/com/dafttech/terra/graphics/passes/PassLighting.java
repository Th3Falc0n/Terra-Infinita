package com.dafttech.terra.graphics.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.lighting.Light;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Player;

public class PassLighting extends RenderingPass {
    FrameBuffer buffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    List<Light> lights = new ArrayList<Light>();

    public void addLight(Light l) {
        lights.add(l);
    }

    @Override
    public void applyPass(AbstractScreen screen, Player player, World w, Object... arguments) {
        buffer.begin();

        Gdx.graphics.getGL20().glClearColor(.2f, .2f, .2f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for (Light l : lights) {
            l.drawToLightmap(screen, player);
        }

        screen.shr.begin(ShapeType.FilledRectangle);

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        for (int x = (int) player.getPosition().x / BLOCK_SIZE - sx; x < (int) player.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) player.getPosition().y / BLOCK_SIZE - sy; y < (int) player.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (x >= 0 && x < w.map.length && y >= 0 && y < w.map[0].length && w.map[x][y] == null) {
                    // TODO: If tile can see sky, draw lightmap with sun color
                }
            }
        }

        screen.shr.end();

        buffer.end();

        RenderingPass.rpGaussian.applyPass(screen, player, w, buffer.getColorBufferTexture(), buffer);

        screen.batch.setShader(null);
        screen.batch.setBlendFunction(GL10.GL_DST_COLOR, GL10.GL_ZERO);
        screen.batch.enableBlending();

        screen.batch.begin();
        screen.batch.draw(buffer.getColorBufferTexture(), 0, 0);
        screen.batch.end();

        lights.clear();
    }

}

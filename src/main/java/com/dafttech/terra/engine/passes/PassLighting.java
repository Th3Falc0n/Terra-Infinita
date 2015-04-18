package com.dafttech.terra.engine.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.FloatFrameBuffer;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import org.lolhens.eventmanager.Event;
import org.lolhens.eventmanager.EventListener;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class PassLighting extends RenderingPass {
    FrameBuffer buffer = new FloatFrameBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    public int sunlevel = BLOCK_SIZE;

    public Rectangle getSunlightRect(Tile t, Entity pointOfView) {
        Vector2 v = t.getPosition().toScreenPos(pointOfView);
        if (t.sunlightFilter == null) {
            return new Rectangle(v.x - sunlevel, 0, BLOCK_SIZE + sunlevel * 2, v.y + sunlevel);
        } else {
            Vector2 f = t.sunlightFilter.getPosition().toScreenPos(pointOfView);
            return new Rectangle(v.x - sunlevel, f.y, BLOCK_SIZE + sunlevel * 2, v.y - f.y + sunlevel);
        }
    }

    @EventListener(value = "WINRESIZE")
    public void onResize(Event e) {
        buffer = new FloatFrameBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @SuppressWarnings("unused")
    @Override
    public void applyPass(AbstractScreen screen, Entity pointOfView, World world, Object... arguments) {
        buffer.begin();

        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 0);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        screen.batch.enableBlending();
        screen.batch.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE);

        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        boolean resetToWhite = false;
        Color nextClr = Color.WHITE;
        Color activeClr = Color.WHITE;

        screen.batch.begin();

        screen.shr.begin(ShapeType.FilledRectangle);
        screen.shr.setColor(nextClr);

        for (int x = (int) pointOfView.getPosition().x / BLOCK_SIZE - sx; x < (int) pointOfView.getPosition().x / BLOCK_SIZE + sx; x++) {
            for (int y = (int) pointOfView.getPosition().y / BLOCK_SIZE - sy; y < (int) pointOfView.getPosition().y / BLOCK_SIZE + sy; y++) {
                if (world.getTile(x, y) != null) {
                    if (world.getTile(x, y).receivesSunlight) {

                        nextClr = world.getTile(x, y).getSunlightColor();

                        if (nextClr != activeClr) {
                            activeClr = nextClr;

                            screen.shr.end();
                            screen.shr.setColor(nextClr);
                            screen.shr.begin(ShapeType.FilledRectangle);
                        }

                        Rectangle rect = getSunlightRect(world.getTile(x, y), pointOfView);

                        screen.shr.filledRect(rect.x, rect.y, rect.width, rect.height);
                    }

                    if (world.getTile(x, y).isLightEmitter() && world.getTile(x, y).getEmittedLight() != null) {
                        world.getTile(x, y).getEmittedLight().drawToLightmap(screen, pointOfView);
                    }
                }
            }
        }

        screen.shr.end();

        screen.batch.end();

        screen.batch.begin();

        for (Chunk chunk : world.localChunks.values()) {
            for (Entity entity : chunk.getLocalEntities()) {
                if (entity.isLightEmitter() && entity.getEmittedLight() != null && world.isInRenderRange(entity.getPosition())) {
                    entity.getEmittedLight().drawToLightmap(screen, pointOfView);
                }
            }
        }

        screen.batch.end();

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

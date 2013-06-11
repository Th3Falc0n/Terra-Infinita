package com.dafttech.terra.graphics.passes;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    public void applyPass(AbstractScreen screen, Player player, World w) {
        buffer.begin();
        Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        
        for(Light l : lights) {
            l.drawToLightmap(screen, player);
        }
        
        buffer.end();
        
        screen.batch.setBlendFunction(GL11.GL_DST_COLOR, GL11.GL_ZERO);
        screen.batch.enableBlending();
        screen.batch.begin();
        
        screen.batch.draw(buffer.getColorBufferTexture(), 0, 0);
        screen.batch.end();
        
        lights.clear();
    }

}

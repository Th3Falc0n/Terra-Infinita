package com.dafttech.terra.graphics.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.shaders.ShaderLibrary;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.entities.Player;

public class PassGaussian extends RenderingPass {
    FrameBuffer bfPass1 = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    FrameBuffer bfPass2 = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    
    Texture pass;

    @Override
    public void applyPass(AbstractScreen screen, Player player, World w, Object... arguments) {
        if(!(arguments[0] instanceof Texture)) throw new IllegalArgumentException("Need a texture to draw");
        if(!(arguments[1] instanceof FrameBuffer)) throw new IllegalArgumentException("Need a destination buffer");
        pass = (Texture)arguments[0];
        
        bfPass1.begin();
        
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        screen.batch.setShader(ShaderLibrary.getShader("GaussV"));

        screen.batch.begin();
        screen.batch.draw(pass, 0, 0);
        screen.batch.end();
        
        bfPass1.end();
        
        pass = bfPass1.getColorBufferTexture();
        
        bfPass2.begin();
        
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        screen.batch.setShader(ShaderLibrary.getShader("GaussH"));

        screen.batch.begin();
        screen.batch.draw(pass, 0, 0);
        screen.batch.end();
        
        bfPass2.end();
        
        pass = bfPass2.getColorBufferTexture();

        ((FrameBuffer)(arguments[1])).begin();
        
        screen.batch.setShader(null);

        screen.batch.begin();
        screen.batch.draw(pass, 0, 0);
        screen.batch.end();
        
        ((FrameBuffer)(arguments[1])).end();
    }

}

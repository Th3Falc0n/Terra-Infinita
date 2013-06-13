package com.dafttech.terra.graphics.passes;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.shaders.ShaderLibrary;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.entities.Player;

public class PassGaussian extends RenderingPass {
    FrameBuffer buffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    
    Texture pass;

    @Override
    public void applyPass(AbstractScreen screen, Player player, World w, Object... arguments) {
        if(!(arguments[0] instanceof Texture)) throw new IllegalArgumentException("Need a texture to draw");
        pass = (Texture)arguments[0];
        
        buffer.begin();
        
        screen.batch.setShader(ShaderLibrary.getShader("GaussV"));

        screen.batch.begin();
        screen.batch.draw(pass, 0, 0);
        screen.batch.end();
        
        buffer.end();
        
        pass = buffer.getColorBufferTexture();
        
        buffer.begin();
        
        screen.batch.setShader(ShaderLibrary.getShader("GaussH"));

        screen.batch.begin();
        screen.batch.draw(pass, 0, 0);
        screen.batch.end();
        
        buffer.end();
        
        pass = buffer.getColorBufferTexture();

        screen.batch.setShader(null);

        screen.batch.begin();
        screen.batch.draw(pass, 0, 0);
        screen.batch.end();
    }

}

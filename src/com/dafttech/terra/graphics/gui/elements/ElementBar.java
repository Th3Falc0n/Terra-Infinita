package com.dafttech.terra.graphics.gui.elements;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;

public class ElementBar extends GUIElement {
    private static FrameBuffer ciBuffer = new FrameBuffer(Format.RGBA8888, 128, 16, false);
    Matrix4 bufferMatrix = new Matrix4().setToOrtho(0, 128, 16, 0, 0, 1);
    
    public TextureRegion imageMask;
    
    public float value, maxValue;
    
    private Color clr;

    public ElementBar(Vector2 p, Color c, float mv) {
        super(p, new Vector2(128, 16));
        
        clr = c;
        clr.a = 1;
        maxValue = mv;
        value = 50;
        
        image = Resources.GUI.getImage("bar");
        imageMask = Resources.GUI.getImage("bar_mask");
    }
    
    public void setValue(float v) {
        value = v;
    }

    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        screen.batch.setShader(null);
        
        ciBuffer.begin();
        screen.batch.setProjectionMatrix(bufferMatrix);
        screen.shr.setProjectionMatrix(bufferMatrix);

        Gdx.graphics.getGL20().glClearColor(1, 1, 1, 0);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        screen.batch.disableBlending();
        screen.batch.begin();

        screen.batch.setColor(clr);
        screen.batch.draw(imageMask, 0, 0);
        
        screen.batch.end();

        screen.shr.setColor(0, 0, 0, 0);
        screen.shr.begin(ShapeType.FilledRectangle);
        float w = 124f - (124f * (value / maxValue));
        screen.shr.filledRect(126 - w, 0, w, 16);
        screen.shr.end();

        ciBuffer.end();
        
        TextureRegion tr = new TextureRegion(ciBuffer.getColorBufferTexture());

        screen.batch.setProjectionMatrix(screen.projection);
        screen.shr.setProjectionMatrix(screen.projection);
        screen.batch.setColor(Color.WHITE);
        screen.batch.enableBlending();
        screen.batch.begin();

        screen.batch.draw(tr, position.x, position.y);
        screen.batch.draw(image, position.x, position.y);
        
        screen.batch.end();
    }
}

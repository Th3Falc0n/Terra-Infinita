package com.dafttech.terra.graphics.gui.elements;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;

public class ElementBar extends GUIElement {
    private static FrameBuffer ciBuffer = new FrameBuffer(Format.RGBA8888, 128, 16, false);
    
    public TextureRegion imageMask;
    
    public float value, maxValue;
    
    private Color clr;

    public ElementBar(Vector2 p, Color c, float mv) {
        super(p, new Vector2(128, 16));
        
        clr = c;
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
        ciBuffer.begin();

        screen.batch.disableBlending();
        screen.batch.begin();
        
        screen.batch.setColor(clr);
        screen.batch.draw(imageMask, 0, 0);
        screen.shr.begin(ShapeType.FilledRectangle);
        screen.shr.setColor(0, 0, 0, 0);
        float w = 124f - (124f * (value / maxValue));
        screen.shr.filledRect(126 - w, 0, w, 16);
        screen.shr.end();
        
        screen.batch.end();
        
        ciBuffer.end();
        
        TextureRegion tr = new TextureRegion(ciBuffer.getColorBufferTexture());
        tr.flip(false, true);

        screen.batch.setColor(Color.WHITE);
        screen.batch.enableBlending();
        screen.batch.begin();
        
        screen.batch.draw(tr, position.x, position.y);
        screen.batch.draw(image, position.x, position.y);
        
        screen.batch.end();
    }
}

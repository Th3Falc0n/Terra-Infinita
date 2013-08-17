package com.dafttech.terra.engine.gui.elements;

import java.awt.RenderingHints.Key;

import javax.print.attribute.standard.DateTimeAtCompleted;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.input.FocusManager;
import com.dafttech.terra.engine.input.IFocusableTyping;
import com.dafttech.terra.engine.input.handlers.IStringInputHandler;
import com.dafttech.terra.resources.Resources;

public class ElementInputLabel extends GUIElement implements IFocusableTyping {
    private String text = "";
    private String renderText = "";
    public Color clr = Color.WHITE;
    
    IStringInputHandler handler = null;
    
    private float time = 0;

    public ElementInputLabel(Vector2 p, IStringInputHandler h) {
        super(p, null);
        
        handler = h;
    }
    
    public void setColor(Color c) {
        clr = c;
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        time += delta;

        renderText = ">" + text + ((time % 1f) < 0.5 && FocusManager.hasTypeFocus(this) ? "_" : "");

        TextBounds bnds = Resources.GUI_FONT.getBounds(text);
        size = new Vector2(bnds.width, bnds.height);
    }

    @Override
    public void draw(AbstractScreen screen) {
        Vector2 p = getScreenPosition();
        
        screen.batch.begin();
        Resources.GUI_FONT.setColor(clr);
        Resources.GUI_FONT.draw(screen.batch, renderText, p.x, 6 + p.y);
        screen.batch.end();
    }
    
    public boolean beginStringInput() {
        return FocusManager.acquireTypeFocus(this);
    }

    @Override
    public void onKeyTyped(char c) {
        if(c == '\b' && text.length() >= 1) {
            text = text.substring(0, text.length() - 1);
        }
        
        if(!Character.isIdentifierIgnorable(c)) {
            text += c;
        }
    }

    @Override
    public void onKeyDown(int i) {
        if(i == Keys.ENTER) {
            handler.handleInput(text);
            text = "";
            FocusManager.releaseTypeFocus(this);
        }
    }
}

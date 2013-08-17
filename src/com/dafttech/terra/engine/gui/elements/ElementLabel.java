package com.dafttech.terra.engine.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.resources.Resources;

public class ElementLabel extends GUIElement {
    private String text = "Button";
    public Color clr = Color.WHITE;

    public ElementLabel(Vector2 p, String txt) {
        super(p, null);

        text = txt;

        TextBounds bnds = Resources.GUI_FONT.getBounds(text);
        size = new Vector2(bnds.width, bnds.height);
    }

    public void setText(String txt) {
        text = txt;

        TextBounds bnds = Resources.GUI_FONT.getBounds(text);
        size = new Vector2(bnds.width, bnds.height);
    }
    
    public void setColor(Color c) {
        clr = c;
    }

    @Override
    public void draw(AbstractScreen screen) {
        Vector2 p = getScreenPosition();
        
        screen.batch.begin();
        Resources.GUI_FONT.setColor(clr);
        Resources.GUI_FONT.draw(screen.batch, text, p.x, 6 + p.y);
        screen.batch.end();
    }
}

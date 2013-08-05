package com.dafttech.terra.graphics.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;

public class ElementLabel extends GUIElement {
    private CharSequence text = "Button";

    public ElementLabel(Vector2 p, String txt) {
        super(p, null);
        
        text = txt;

        TextBounds bnds = Resources.BUTTON_FONT.getBounds(text);
        size = new Vector2(bnds.width, bnds.height);
    }
    
    public void setText(String txt) {
        text = txt;

        TextBounds bnds = Resources.BUTTON_FONT.getBounds(text);
        size = new Vector2(bnds.width, bnds.height);
    }

    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        screen.batch.begin();
        Resources.BUTTON_FONT.setColor(Color.WHITE);
        Resources.BUTTON_FONT.draw(screen.batch, text, position.x + origin.x + size.x / 2 - Resources.BUTTON_FONT.getBounds(text).width / 2, 6 + position.y + origin.y);
        screen.batch.end();
    }
}

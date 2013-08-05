package com.dafttech.terra.graphics.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;

public abstract class ElementButton extends GUIElement {
    private CharSequence text = "Button";

    public ElementButton(Vector2 p, String txt) {
        super(p, new Vector2(100, 20));

        text = txt;
        image = Resources.GUI.getImage("button");
    }

    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        if(mouseHover) {
            screen.batch.setColor(Color.GREEN);
            Resources.BUTTON_FONT.setColor(Color.GREEN);
        }
        else
        {
            screen.batch.setColor(Color.WHITE);
            Resources.BUTTON_FONT.setColor(Color.WHITE);
        }
        
        super.draw(screen, origin);
        
        screen.batch.begin();
        Resources.BUTTON_FONT.draw(screen.batch, text, position.x + origin.x + size.x / 2 - Resources.BUTTON_FONT.getBounds(text).width / 2, 6 + position.y + origin.y);
        screen.batch.end();

        screen.batch.setColor(Color.WHITE);
    }
}

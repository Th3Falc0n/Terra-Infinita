package com.dafttech.terra.graphics.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;

public abstract class ElementLabel extends GUIElement {
    private CharSequence text = "Button";

    public ElementLabel(Vector2 p, String txt) {
        super(p, new Vector2(100, 20));

        text = txt;
    }

    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        screen.batch.begin();
        Resources.BUTTON_FONT.draw(screen.batch, text, position.x + origin.x + size.x / 2 - Resources.BUTTON_FONT.getBounds(text).width / 2, 6 + position.y + origin.y);
        screen.batch.end();

        screen.batch.setColor(Color.WHITE);
    }
}

package com.dafttech.terra.engine.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.resources.Resources;

public abstract class ElementButton extends GUIElement {
    private CharSequence text = "Button";

    public ElementButton(Vector2 p, String txt) {
        super(p, new Vector2(100, 20));

        text = txt;
        image = Resources.GUI.getImage("button");

        size = new Vector2(100, 20);
    }

    @Override
    public void draw(AbstractScreen screen) {
        Vector2 p = getScreenPosition();

        if (mouseHover) {
            screen.batch.setColor(Color.GREEN);
            Resources.GUI_FONT.setColor(Color.GREEN);
        } else {
            screen.batch.setColor(Color.WHITE);
            Resources.GUI_FONT.setColor(Color.WHITE);
        }

        super.draw(screen);

        screen.batch.begin();
        Resources.GUI_FONT.draw(screen.batch, text, p.x + size.x / 2 - Resources.GUI_FONT.getBounds(text).width / 2, 6 + p.y);
        screen.batch.end();

        screen.batch.setColor(Color.WHITE);
    }
}

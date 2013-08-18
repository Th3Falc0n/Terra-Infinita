package com.dafttech.terra.engine.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.GUIObject;

public abstract class GUIElement extends GUIObject {
    public TextureRegion image;

    public GUIElement(Vector2 p, Vector2 s) {
        super(p, s);
    }

    @Override
    public void draw(AbstractScreen screen) {
        Vector2 p = getScreenPosition();

        screen.batch.begin();
        screen.batch.draw(image, p.x, p.y);
        screen.batch.end();
        screen.batch.setColor(Color.WHITE);
    }
}

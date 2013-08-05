package com.dafttech.terra.graphics.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.gui.GUIObject;

public abstract class GUIElement extends GUIObject {
    public TextureRegion image;
    
    public GUIElement(Vector2 p, Vector2 s) {
        super(p, s);
    }

    public void draw(AbstractScreen screen, Vector2 origin) {
        screen.batch.begin();
        screen.batch.draw(image, position.x + origin.x, position.y + origin.y);
        screen.batch.end();
        screen.batch.setColor(Color.WHITE);
    }
}

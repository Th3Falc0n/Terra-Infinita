package com.dafttech.terra.graphics.gui.elements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;

public abstract class ElementButton extends GUIElement {
    private TextureRegion image;
    private TextureRegion activeImage;
    
    public ElementButton(Vector2 p, Vector2 s, TextureRegion img) {
        super(p, s);
        
        image = img;
        activeImage = image;
    }

    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        screen.batch.draw(activeImage, position.x, position.y);
    }
}

package com.dafttech.terra.graphics.gui.elements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;

public abstract class ElementButton extends GUIElement {
    private TextureRegion image;
    private TextureRegion activeImage;
    
    private CharSequence text = "Button";
    
    public ElementButton(Vector2 p, Vector2 s, TextureRegion img, String txt) {
        super(p, s);
                
        text = txt;
        image = img;
        activeImage = image;
    }

    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        screen.batch.begin();
        screen.batch.draw(activeImage, position.x, position.y);
        screen.batch.end();
        
        screen.batch.begin();
        Resources.BUTTON_FONT.setColor(1, 1, 1, 1);
        Resources.BUTTON_FONT.draw(screen.batch, text, position.x + size.x/2 - Resources.BUTTON_FONT.getBounds(text).width / 2, 6);
        screen.batch.end();
    }
}

package com.dafttech.terra.graphics.gui.elements;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.gui.GUIObject;
import com.dafttech.terra.world.Vector2;

public abstract class GUIElement extends GUIObject {
    public GUIElement(Vector2 p, Vector2 s) {
        super(p, s);
    }
    
    public abstract void draw(AbstractScreen screen, Vector2 origin);
}

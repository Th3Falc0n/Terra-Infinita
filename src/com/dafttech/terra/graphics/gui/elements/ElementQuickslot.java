package com.dafttech.terra.graphics.gui.elements;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;

public class ElementQuickslot extends ElementSlot {

    public ElementQuickslot(Vector2 p) {
        super(p);
    }

    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        super.draw(screen, origin);
        
        //TODO: Add Key Text
    }
}

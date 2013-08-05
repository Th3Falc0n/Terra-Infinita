package com.dafttech.terra.graphics.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entities.items.IItemSlot;

public class ElementSlot extends GUIElement {
    IItemSlot assignedSlot = null;
    
    public ElementSlot(Vector2 p) {
        super(p, new Vector2(32, 32));

        image = Resources.GUI.getImage("slot");
    }
    
    @Override
    public void draw(AbstractScreen screen, Vector2 origin) {
        super.draw(screen, origin);
        
        if(assignedSlot != null && assignedSlot.getContent() != null) {
            assignedSlot.getContent().getItem().drawInventory(screen, new Vector2(position.x + origin.x, position.y + origin.y));
        }
    }
}

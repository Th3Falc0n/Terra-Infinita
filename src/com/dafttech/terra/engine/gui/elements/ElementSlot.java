package com.dafttech.terra.engine.gui.elements;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.inventories.ItemSlot;
import com.dafttech.terra.resources.Resources;

public class ElementSlot extends GUIElement {
    ItemSlot assignedSlot = null;

    public ElementSlot(Vector2 p) {
        super(p, new Vector2(32, 32));

        image = Resources.GUI.getImage("slot");
    }

    @Override
    public void draw(AbstractScreen screen) {
        super.draw(screen);
        Vector2 p = getScreenPosition();

        if (assignedSlot != null && assignedSlot.getStack().getContent() != null) {
            assignedSlot.getStack().getContent().drawInventory(screen, new Vector2(p.x, p.y));
        }
    }
}

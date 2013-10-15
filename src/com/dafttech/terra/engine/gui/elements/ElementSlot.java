package com.dafttech.terra.engine.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.inventories.Inventory;
import com.dafttech.terra.resources.Resources;

public class ElementSlot extends GUIElement {
    public Item assignedItem = null;
    public Inventory assignedInventory = null;

    public ElementSlot(Vector2 p) {
        super(p, new Vector2(32, 32));

        image = Resources.GUI.getImage("slot");
    }

    @Override
    public void draw(AbstractScreen screen) {
        super.draw(screen);
        Vector2 p = getScreenPosition();
        
        screen.batch.begin();

        if(assignedItem != null) {
            Resources.GUI_FONT.setColor(Color.WHITE);
            Resources.GUI_FONT.draw(screen.batch, "" + assignedInventory.getAmount(assignedItem), p.x, 6 + p.y);
            
            assignedItem.drawInventory(screen, p);
        }
        
        screen.batch.end();
    }
}

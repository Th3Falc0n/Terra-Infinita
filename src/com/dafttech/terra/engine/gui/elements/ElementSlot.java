package com.dafttech.terra.engine.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.inventories.Inventory;
import com.dafttech.terra.game.world.items.persistence.Prototype;
import com.dafttech.terra.resources.Resources;

public class ElementSlot extends GUIElement {
    private Prototype assignedType = null;
    private float cooldownTime = 0;
    public Inventory assignedInventory = null;

    public boolean active = false;

    public ElementSlot(Vector2 p) {
        super(p, new Vector2(32, 32));

        image = Resources.GUI.getImage("slot");
    }

    public boolean useAssignedItem(Player causer, Vector2 pos, boolean leftClick) {
        if (assignedInventory.getAmount(assignedType) > 0 && causer.worldObj.time > cooldownTime) {
            Item item = (Item) assignedType.toGameObject();
            if (!leftClick) {
                if ((!leftClick && item.use(causer, pos)) || (leftClick && item.leftClick(causer, pos))) {
                    assignedInventory.remove(assignedType, item.getUsedItemNum(causer, pos, leftClick));
                    setCooldownTime(causer.worldObj, item.getNextUseDelay(causer, pos, leftClick));
                    return true;
                }
            }
        }
        return false;
    }

    public void assignItem(Item item, Inventory inventory) {
        assignedType = item.toPrototype();
        assignedInventory = inventory;
    }

    public void setCooldownTime(World world, float cooldownTime) {
        this.cooldownTime = world.time + cooldownTime;
    }

    @Override
    public void draw(AbstractScreen screen) {
        screen.batch.setColor(active ? Color.YELLOW : Color.WHITE);
        super.draw(screen);
        Vector2 p = getScreenPosition();

        screen.batch.begin();

        if (assignedType != null) {
            ((Item) assignedType.toGameObject()).drawInventory(p, screen);

            Resources.GUI_FONT.setColor(active ? Color.YELLOW : Color.WHITE);
            Resources.GUI_FONT.draw(screen.batch, "" + assignedInventory.getAmount(assignedType), p.x, 6 + p.y);
        }

        screen.batch.end();
    }
}

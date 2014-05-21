package com.dafttech.terra.engine.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.MouseSlot;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.inventories.Inventory;
import com.dafttech.terra.game.world.items.inventories.Stack;
import com.dafttech.terra.game.world.items.persistence.Prototype;
import com.dafttech.terra.resources.Resources;

public class ElementSlot extends GUIElement {
    private float cooldownTime = 0;
    public Stack assignedStack = null;

    public boolean active = false;

    public ElementSlot(Vector2 p) {
        super(p, new Vector2(32, 32));

        image = Resources.GUI.getImage("slot");
    }

    public boolean useAssignedItem(Player causer, Vector2 pos, boolean leftClick) {
        if (assignedStack != null && assignedStack.amount > 0 && causer.worldObj.time > cooldownTime) {
            if ((!leftClick && assignedStack.use(causer, pos))) {
                setCooldownTime(causer.worldObj, ((Item)assignedStack.type.toGameObject()).getNextUseDelay(causer, pos, leftClick));
                return true;
            }
        }

        return false;
    }

    public void setCooldownTime(World world, float cooldownTime) {
        this.cooldownTime = world.time + cooldownTime;
    }

    @Override
    public void onClick(int button) {
        if (button == 0) {
            if (MouseSlot.getAssignedStack() != null && assignedStack == null) {
                assignedStack = MouseSlot.popAssignedStack();
            } else if (MouseSlot.canAssignStack() && assignedStack != null) {
                MouseSlot.assignStack(assignedStack);
                assignedStack = null;
            } else if (MouseSlot.getAssignedStack() != null && assignedStack != null) {
                Stack at = MouseSlot.popAssignedStack();
                MouseSlot.assignStack(assignedStack);
                assignedStack = at;
            }
        }
    }

    @Override
    public void draw(AbstractScreen screen) {
        screen.batch.setColor(active ? Color.YELLOW : Color.WHITE);
        super.draw(screen);
        Vector2 p = getScreenPosition();

        screen.batch.begin();

        if (assignedStack != null) {
            ((Item) assignedStack.type.toGameObject()).drawInventory(p, screen);

            Resources.GUI_FONT.setColor(active ? Color.YELLOW : Color.WHITE);
            Resources.GUI_FONT.draw(screen.batch, "" + assignedStack.amount, p.x, 6 + p.y);
        }

        screen.batch.end();
    }
    
    public void assignStack(Stack stack) {
        assignedStack = stack;
    }
}

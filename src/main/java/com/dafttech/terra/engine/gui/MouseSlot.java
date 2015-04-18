package com.dafttech.terra.engine.gui;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.elements.ElementSlot;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.inventories.Stack;
import org.lolhens.eventmanager.Event;
import org.lolhens.eventmanager.EventListener;

public class MouseSlot {
    private static class MouseRenderSlot extends ElementSlot {
        public MouseRenderSlot(Vector2 p) {
            super(p);
            Events.EVENTMANAGER.registerEventListener(this);
        }

        @EventListener("MOUSEMOVE")
        public void onEventMouseMove(Event event) {
            int x = event.in.get(1, Integer.class);
            int y = event.in.get(2, Integer.class);

            position.x = x;
            position.y = y;
        }

        @Override
        public void draw(AbstractScreen screen) {
            Vector2 p = getScreenPosition();

            screen.batch.begin();

            if (assignedStack != null) {
                ((Item) assignedStack.type.toGameObject()).drawInventory(p, screen);
            }

            screen.batch.end();
        }
    }

    static private Stack assignedStack = null;
    static private MouseRenderSlot renderSlot = new MouseRenderSlot(new Vector2(0, 0));

    public static void init() {
    }

    public static ElementSlot getRenderSlot() {
        return renderSlot;
    }

    public static boolean canAssignStack() {
        return assignedStack == null;
    }

    public static Stack getAssignedStack() {
        return assignedStack;
    }

    public static Stack popAssignedStack() {
        Stack at = assignedStack;
        assignedStack = null;
        renderSlot.assignStack(null);
        return at;
    }

    public static boolean assignStack(Stack stack) {
        if (assignedStack == null) {
            assignedStack = stack;
            renderSlot.assignStack(assignedStack);
            return true;
        }
        return false;
    }
}

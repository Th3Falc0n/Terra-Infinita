package com.dafttech.terra.engine.gui;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.elements.ElementSlot;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.persistence.Prototype;
import com.dafttech.terra.resources.Resources;

public class MouseSlot {       
    private static class MouseRenderSlot extends ElementSlot {
        public MouseRenderSlot(Vector2 p) {
            super(p);
            Events.EVENTMANAGER.registerEventListener(this);
        }
        
        @EventListener("MOUSEMOVE")
        public void onEventMouseMove(Event event) {
            int x = (int) event.getInput()[1];
            int y = (int) event.getInput()[2];

            position.x = x;
            position.y = y;
        }
        
        @Override
        public void draw(AbstractScreen screen) {
            Vector2 p = getScreenPosition();

            screen.batch.begin();

            if (assignedType != null) {
                ((Item) assignedType.toGameObject()).drawInventory(p, screen);
            }

            screen.batch.end();
        }
    }
    
    static private Prototype assignedType = null;
    static private MouseRenderSlot renderSlot = new MouseRenderSlot(new Vector2(0, 0));
    
    public static void init() {
    }
    
    public static ElementSlot getRenderSlot() {
        return renderSlot;
    }
    
    public static boolean canAssignType() {
        return assignedType == null;
    }
    
    public static Prototype getAssignedType() {
        return assignedType;
    }
    
    public static Prototype popAssignedType() {
        Prototype at = assignedType;
        assignedType = null;
        renderSlot.assignType(null);
        return at;
    }
    
    public static boolean assignType(Prototype type) {
        if (assignedType == null) {
            assignedType = type;
            renderSlot.assignType(assignedType);
            return true;
        }
        return false;
    }
}

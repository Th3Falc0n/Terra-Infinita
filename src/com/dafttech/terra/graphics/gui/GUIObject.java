package com.dafttech.terra.graphics.gui;

import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.event.Events;
import com.dafttech.terra.graphics.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.world.Vector2;

public abstract class GUIObject {
    public Vector2 position;
    public Vector2 size;

    public boolean mouseHover = false;
    
    boolean registeredEvents = false;
    GUIAnchorSet assignedAnchors = null;

    public GUIObject(Vector2 p, Vector2 s) {
        position = p;
        size = s;
    }

    public void assignAnchorSet(GUIAnchorSet set) {
        if(!registeredEvents) {
            Events.EVENTMANAGER.registerEventListener(this);
            registeredEvents = true;
        }
        
        assignedAnchors = set;
        assignedAnchors.applyAnchorSet(this);
    }
    
    @EventListener(events = { "WINRESIZE" })
    public void onWinResize(Event e) {
        assignedAnchors.applyAnchorSet(this);
    }
    
    public void onClick(int button) {

    }

    public void onMouseIn() {

    }

    public void onMouseOut() {

    }
}

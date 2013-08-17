package com.dafttech.terra.engine.gui;

import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.GUIContainer;
import com.dafttech.terra.game.Events;

public abstract class GUIObject {
    public Vector2 position;
    public Vector2 size;

    public boolean mouseHover = false;

    boolean registeredEvents = false;
    GUIAnchorSet assignedAnchors = null;
    GUIContainer container;

    String tooltipText = "";

    public GUIObject(Vector2 p, Vector2 s) {
        position = p;
        size = s;

        Events.EVENTMANAGER.registerEventListener(this);
    }
    
    public abstract void draw(AbstractScreen screen);

    public Vector2 getScreenPosition() {
        if(container != null) {
            return container.getScreenPosition().addNew(position);
        }
        return position;
    }
    
    public void setTooltip(String txt) {
        tooltipText = txt;
    }

    public void assignAnchorSet(GUIAnchorSet set) {
        assignedAnchors = set;
    }

    public void applyAnchorSet(GUIAnchorSet set) {
        if(container == null && set.isContainerDependent()) throw new IllegalStateException("AnchorSet needs container");
        set.applyAnchorSet(this, container);
    }

    public void applyAssignedAnchorSet() {
        if (assignedAnchors != null) {
            applyAnchorSet(assignedAnchors);
        }
    }

    public void update(float delta) {
        if (assignedAnchors != null && assignedAnchors.needsApplyOnFrame()) applyAssignedAnchorSet();
    }

    @EventListener(events = { "WINRESIZE" })
    public void onWinResize(Event e) {
        applyAssignedAnchorSet();
    }

    public void onClick(int button) {

    }

    public void onMouseIn() {
        if (tooltipText != "") {
            Tooltip.setText(tooltipText);
        }
    }

    public void onMouseOut() {
        if (tooltipText != "") {
            Tooltip.setText("");
        }
    }

    public void setContainer(GUIContainer guiContainer) {
        container = guiContainer;
    }
}

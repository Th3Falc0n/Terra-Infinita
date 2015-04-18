package com.dafttech.terra.engine.gui;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.GUIContainer;
import com.dafttech.terra.game.Events;
import org.lolhens.eventmanager.EventListener;

public abstract class GUIObject {
    public Vector2 position;
    public Vector2 size;

    public boolean mouseHover = false;

    protected boolean registeredEvents = false;
    protected GUIAnchorSet assignedAnchors = null;
    public GUIContainer container;

    String tooltipText = "";

    public GUIObject(Vector2 p, Vector2 s) {
        position = p;
        size = s;

        Events.EVENTMANAGER.registerEventListener(this);
    }

    public abstract void draw(AbstractScreen screen);

    public Vector2 getScreenPosition() {
        if (container != null) {
            return container.getScreenPosition().addNew(position);
        }
        return position;
    }

    public boolean providesActiveHierarchy() {
        return false;
    }

    public boolean isInActiveHierarchy() {
        if (providesActiveHierarchy()) return true;

        GUIContainer check = container;

        if (check == null) return false;
        if (check.providesActiveHierarchy()) return true;

        while (check.container != null) {
            if (check.container.providesActiveHierarchy()) return true;
            check = check.container;
        }

        return false;
    }

    public void setTooltip(String txt) {
        tooltipText = txt;
    }

    public void assignAnchorSet(GUIAnchorSet set) {
        assignedAnchors = set;
    }

    public void applyAnchorSet(GUIAnchorSet set) {
        if (container != null || !set.isContainerDependent()) {
            set.applyAnchorSet(this, container);
        }
    }

    public void applyAssignedAnchorSet() {
        if (assignedAnchors != null) {
            applyAnchorSet(assignedAnchors);
        }
    }

    public void update(float delta) {
        if (assignedAnchors != null && assignedAnchors.needsApplyOnFrame()) applyAssignedAnchorSet();
    }

    @EventListener("WINRESIZE")
    public void onWinResize() {
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

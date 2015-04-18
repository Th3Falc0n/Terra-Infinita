package com.dafttech.terra.engine.gui.modules;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.*;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.elements.ElementBar;
import com.dafttech.terra.engine.gui.elements.ElementLabel;
import com.dafttech.terra.engine.gui.elements.ElementSlot;
import com.dafttech.terra.game.Events;
import org.lolhens.eventmanager.Event;
import org.lolhens.eventmanager.EventListener;

public class ModuleHUDBottom extends GUIModule {
    public ElementSlot[] slots = new ElementSlot[8];
    private int activeSlot = 0;
    public ElementBar healthBar, apBar;

    @EventListener(value = "SCROLL", priority = -1)
    public void onScroll(Event event) {
        int prev = activeSlot;
        int dir = event.in.get(0, Integer.class);

        activeSlot += dir;
        if (activeSlot < 0) activeSlot = 7;
        if (activeSlot > 7) activeSlot = 0;

        slots[prev].active = false;
        slots[activeSlot].active = true;
    }

    public ElementSlot getActiveSlot() {
        return slots[activeSlot];
    }

    @Override
    public void create() {
        Events.EVENTMANAGER.registerEventListener(this);

        container = new ContainerBlock(new Vector2(), new Vector2(312, 80));

        GUIAnchorSet set = new GUIAnchorSet();

        set.addAnchor(new AnchorCenterX());
        set.addAnchor(new AnchorBottom(0.01f));

        container.assignAnchorSet(set);

        for (int i = 0; i < 8; i++) {
            slots[i] = new ElementSlot(new Vector2(i * 40, 0));
            slots[i].assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorBottom(0.01f)));

            container.addObject(slots[i]);
        }

        slots[0].active = true;

        healthBar = new ElementBar(new Vector2(0, 16), Color.RED, 100);
        apBar = new ElementBar(new Vector2(0, 16), Color.BLUE, 100);

        healthBar.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)));
        apBar.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorRight(0)));

        ElementLabel healthLabel, apLabel;

        healthLabel = new ElementLabel(new Vector2(), "HP");
        apLabel = new ElementLabel(new Vector2(), "AP");

        healthLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)));
        apLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorRight(0)));

        container.addObject(healthLabel);
        container.addObject(apLabel);
        container.addObject(healthBar);
        container.addObject(apBar);
    }
}

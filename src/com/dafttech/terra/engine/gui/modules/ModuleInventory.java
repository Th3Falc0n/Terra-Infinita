package com.dafttech.terra.engine.gui.modules;

import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorBottom;
import com.dafttech.terra.engine.gui.anchors.AnchorCenterX;
import com.dafttech.terra.engine.gui.anchors.AnchorLeft;
import com.dafttech.terra.engine.gui.anchors.AnchorTop;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.containers.ContainerList;
import com.dafttech.terra.engine.gui.elements.ElementLabel;
import com.dafttech.terra.game.Events;

public class ModuleInventory extends GUIModule {
    ContainerList invList;

    @EventListener(value = "SCROLL")
    public void onScroll(Event event) {

    }

    @Override
    public void create() {
        Events.EVENTMANAGER.registerEventListener(this);

        container = new ContainerBlock(new Vector2(), new Vector2(312, 200));

        GUIAnchorSet set = new GUIAnchorSet();

        set.addAnchor(new AnchorCenterX());
        set.addAnchor(new AnchorBottom(0.01f));

        container.assignAnchorSet(set);
        ElementLabel invLabel;

        invLabel = new ElementLabel(new Vector2(), "HP");

        invLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)).addAnchor(new AnchorTop(0)));

        container.addObject(invLabel);
    }
}

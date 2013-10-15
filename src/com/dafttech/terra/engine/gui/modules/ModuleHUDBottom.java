package com.dafttech.terra.engine.gui.modules;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorBottom;
import com.dafttech.terra.engine.gui.anchors.AnchorCenterX;
import com.dafttech.terra.engine.gui.anchors.AnchorLeft;
import com.dafttech.terra.engine.gui.anchors.AnchorRight;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.engine.gui.elements.ElementBar;
import com.dafttech.terra.engine.gui.elements.ElementLabel;
import com.dafttech.terra.engine.gui.elements.ElementQuickslot;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.items.inventories.Inventory;

public class ModuleHUDBottom extends GUIModule {
    public ElementQuickslot[] slots = new ElementQuickslot[8];
    public ElementBar healthBar, apBar;

    @Override
    public void create() {
        Events.EVENTMANAGER.registerEventListener(this);

        container = new ContainerBlock(new Vector2(0, 0), new Vector2(312, 80));

        GUIAnchorSet set = new GUIAnchorSet();

        set.addAnchor(new AnchorCenterX());
        set.addAnchor(new AnchorBottom(0.01f));

        container.assignAnchorSet(set);

        for (int i = 0; i < 8; i++) {
            slots[i] = new ElementQuickslot(new Vector2(i * 40, 0));
            slots[i].assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorBottom(0.01f)));

            container.addObject(slots[i]);
        }

        healthBar = new ElementBar(new Vector2(0, 16), Color.RED, 100);
        apBar = new ElementBar(new Vector2(0, 16), Color.BLUE, 100);

        healthBar.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)));
        apBar.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorRight(0)));

        ElementLabel healthLabel, apLabel;

        healthLabel = new ElementLabel(new Vector2(0, 0), "HP");
        apLabel = new ElementLabel(new Vector2(0, 0), "AP");

        healthLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)));
        apLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorRight(0)));

        container.addObject(healthLabel);
        container.addObject(apLabel);
        container.addObject(healthBar);
        container.addObject(apBar);
    }
}

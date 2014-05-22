package com.dafttech.terra.engine.gui.modules;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorBottom;
import com.dafttech.terra.engine.gui.anchors.AnchorCenterX;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.containers.ContainerBlock;
import com.dafttech.terra.game.Events;

public class ModuleCrafting extends GUIModule {

    @Override
    public void create() {
        Events.EVENTMANAGER.registerEventListener(this);

        container = new ContainerBlock(new Vector2(), new Vector2(312, 200));

        GUIAnchorSet set = new GUIAnchorSet();

        set.addAnchor(new AnchorCenterX());
        set.addAnchor(new AnchorBottom(0.12f));

        container.assignAnchorSet(set);
    }

}

package com.dafttech.terra.engine.gui.anchors;

import com.dafttech.terra.engine.gui.GUIObject;
import com.dafttech.terra.engine.gui.containers.GUIContainer;

public class AnchorCenterX extends GUIAnchor {
    @Override
    public void applyAnchor(GUIObject object, GUIContainer container) {
        object.position.x = container.size.x / 2 - object.size.x / 2;
    }
}

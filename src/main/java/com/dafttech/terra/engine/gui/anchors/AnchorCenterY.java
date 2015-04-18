package com.dafttech.terra.engine.gui.anchors;

import com.dafttech.terra.engine.gui.GUIObject;
import com.dafttech.terra.engine.gui.containers.GUIContainer;

public class AnchorCenterY extends GUIAnchor {
    @Override
    public void applyAnchor(GUIObject object, GUIContainer container) {
        object.position.y = container.size.y / 2 - object.size.y / 2;
    }
}

package com.dafttech.terra.engine.gui.anchors;

import com.dafttech.terra.engine.gui.GUIObject;
import com.dafttech.terra.engine.gui.containers.GUIContainer;

public class AnchorTop extends GUIAnchor {
    public float position = 0;

    public AnchorTop(float p) {
        position = p;
    }

    @Override
    public void applyAnchor(GUIObject object, GUIContainer container) {
        object.position.y = container.size.y * position;
    }

}

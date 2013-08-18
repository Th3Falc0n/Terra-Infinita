package com.dafttech.terra.engine.gui.anchors;

import com.dafttech.terra.engine.gui.GUIObject;
import com.dafttech.terra.engine.gui.containers.GUIContainer;

public class AnchorRight extends GUIAnchor {
    public float position = 0;

    public AnchorRight(float p) {
        position = p;
    }

    @Override
    public void applyAnchor(GUIObject object, GUIContainer container) {
        object.position.x = container.size.x * (1f - position) - object.size.x;
    }

}

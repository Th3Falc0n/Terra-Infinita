package com.dafttech.terra.engine.gui.anchors;

import com.dafttech.terra.engine.gui.GUIObject;
import com.dafttech.terra.engine.gui.containers.GUIContainer;

public abstract class GUIAnchor {
    public abstract void applyAnchor(GUIObject object, GUIContainer container);

    public boolean needsApplyOnFrame() {
        return false;
    }

    public boolean isContainerDependent() {
        return true;
    }
}

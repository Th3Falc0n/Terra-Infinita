package com.dafttech.terra.graphics.gui.anchors;

import com.dafttech.terra.graphics.gui.GUIObject;
import com.dafttech.terra.graphics.gui.containers.GUIContainer;

public abstract class GUIAnchor {
    public abstract void applyAnchor(GUIObject object, GUIContainer container);

    public boolean needsApplyOnFrame() {
        return false;
    }
    
    public boolean isContainerDependent() {
        return true;
    }
}

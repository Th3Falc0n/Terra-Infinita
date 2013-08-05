package com.dafttech.terra.graphics.gui.anchors;

import com.dafttech.terra.graphics.gui.GUIObject;

public abstract class GUIAnchor {
    public abstract void applyAnchor(GUIObject object);
    
    public boolean needsApplyOnFrame() {
        return false;
    }
}

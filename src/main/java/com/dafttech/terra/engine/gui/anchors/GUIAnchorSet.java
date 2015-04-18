package com.dafttech.terra.engine.gui.anchors;

import com.dafttech.terra.engine.gui.GUIObject;
import com.dafttech.terra.engine.gui.containers.GUIContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIAnchorSet {
    List<GUIAnchor> anchors = new ArrayList<GUIAnchor>();

    public GUIAnchorSet(GUIAnchor... a) {
        anchors.addAll(Arrays.asList(a));
    }

    public void applyAnchorSet(GUIObject object, GUIContainer container) {
        for (GUIAnchor a : anchors) {
            a.applyAnchor(object, container);
        }
    }

    public boolean needsApplyOnFrame() {
        for (GUIAnchor a : anchors) {
            if (a.needsApplyOnFrame()) return true;
        }
        return false;
    }

    public boolean isContainerDependent() {
        for (GUIAnchor a : anchors) {
            if (a.isContainerDependent()) return true;
        }
        return false;
    }

    public GUIAnchorSet addAnchor(GUIAnchor anchor) {
        anchors.add(anchor);
        return this;
    }
}

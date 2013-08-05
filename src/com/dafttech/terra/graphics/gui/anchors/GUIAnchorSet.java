package com.dafttech.terra.graphics.gui.anchors;

import java.util.ArrayList;
import java.util.List;

import com.dafttech.terra.graphics.gui.GUIObject;

public class GUIAnchorSet {
    List<GUIAnchor> anchors = new ArrayList<GUIAnchor>();
    
    public void applyAnchorSet(GUIObject object) {
        for(GUIAnchor a : anchors) {
            a.applyAnchor(object);
        }
    }
    
    public void addAnchor(GUIAnchor anchor) {
        anchors.add(anchor);
    }
}

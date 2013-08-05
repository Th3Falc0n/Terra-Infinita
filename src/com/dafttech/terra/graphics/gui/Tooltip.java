package com.dafttech.terra.graphics.gui;

import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.graphics.gui.anchors.AnchorMouse;
import com.dafttech.terra.graphics.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.graphics.gui.elements.ElementLabel;
import com.dafttech.terra.graphics.gui.elements.GUIElement;

public class Tooltip {
    public static ElementLabel label;
    
    public static void init() {
        label = new ElementLabel(new Vector2(0, 0), "");
        
        GUIAnchorSet labelSet = new GUIAnchorSet();
        labelSet.addAnchor(new AnchorMouse());
        
        label.assignAnchorSet(labelSet);
    }
    
    public static GUIElement getLabel() {
        return label;
    }
    
    public static void setText(String txt) {
        label.setText(txt);
    }
}

package com.dafttech.terra.engine.gui;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.anchors.AnchorMouse;
import com.dafttech.terra.engine.gui.anchors.GUIAnchorSet;
import com.dafttech.terra.engine.gui.elements.ElementLabel;
import com.dafttech.terra.engine.gui.elements.GUIElement;

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

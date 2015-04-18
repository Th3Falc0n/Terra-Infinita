package com.dafttech.terra.engine.gui.anchors;

import com.dafttech.terra.engine.gui.GUIObject;
import com.dafttech.terra.engine.gui.containers.GUIContainer;

public class AnchorRightNextTo extends GUIAnchor {
    public float distance = 0;
    public GUIObject relTo = null;

    public AnchorRightNextTo(GUIObject obj, float dis) {
        distance = dis;
        relTo = obj;
    }

    @Override
    public void applyAnchor(GUIObject object, GUIContainer container) {
        object.position.x = relTo.position.x + relTo.size.x + distance;
        object.position.y = relTo.position.y;
    }

}

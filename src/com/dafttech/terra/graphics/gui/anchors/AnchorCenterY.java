package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;
import com.dafttech.terra.graphics.gui.containers.GUIContainer;

public class AnchorCenterY extends GUIAnchor {
    @Override
    public void applyAnchor(GUIObject object, GUIContainer container) {
        object.position.y = container.size.y / 2 - object.size.y / 2;
    }
}

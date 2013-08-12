package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;
import com.dafttech.terra.graphics.gui.containers.GUIContainer;

public class AnchorCenterX extends GUIAnchor {
    @Override
    public void applyAnchor(GUIObject object, GUIContainer container) {
        object.position.x = container.size.x / 2 - object.size.x / 2;
    }
}

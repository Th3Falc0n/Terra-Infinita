package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorCenterX extends GUIAnchor {
    @Override
    public void applyAnchor(GUIObject object) {
        object.position.x = Gdx.graphics.getWidth() / 2 - object.size.x / 2;
    }
}

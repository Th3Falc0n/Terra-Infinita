package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorCenterY extends GUIAnchor {
    @Override
    public void applyAnchor(GUIObject object) {
        object.position.y = Gdx.graphics.getHeight() / 2 - object.size.y / 2;
    }
}

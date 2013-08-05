package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorRight extends GUIAnchor {
    public float position = 0;

    public AnchorRight(float p) {
        position = p;
    }

    @Override
    public void applyAnchor(GUIObject object) {
        object.position.x = Gdx.graphics.getWidth() * (1f - position) - object.size.x;
    }

}

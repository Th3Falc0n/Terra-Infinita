package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorLeft extends GUIAnchor {
    public float position = 0;
    
    public AnchorLeft(float p) {
        position = p;
    }
    
    @Override
    public void applyAnchor(GUIObject object) {
        object.position.x = Gdx.graphics.getWidth() * position;
    }

}

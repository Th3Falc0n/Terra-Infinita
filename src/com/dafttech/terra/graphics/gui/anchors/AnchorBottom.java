package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorBottom extends GUIAnchor {
    public float position = 0;
    
    public AnchorBottom(float p) {
        position = p;
    }
    
    @Override
    public void applyAnchor(GUIObject object) {
        object.position.y = Gdx.graphics.getHeight() * (1f - position) - object.size.y;
    }

}

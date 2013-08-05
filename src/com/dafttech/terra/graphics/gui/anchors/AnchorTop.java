package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorTop extends GUIAnchor {
    public float position = 0;
    
    public AnchorTop(float p) {
        
    }
    
    @Override
    public void applyAnchor(GUIObject object) {
        object.position.y = Gdx.graphics.getHeight() * position;
    }

}

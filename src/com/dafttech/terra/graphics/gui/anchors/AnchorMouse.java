package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorMouse extends GUIAnchor {    
    @Override
    public void applyAnchor(GUIObject object) {
        object.position.x = Gdx.input.getX();
        object.position.y = Gdx.input.getY() - object.size.y;
    }
    
    @Override
    public boolean needsApplyOnFrame() {
        return true;
    }
}

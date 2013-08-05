package com.dafttech.terra.graphics.gui.anchors;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.gui.GUIObject;

public class AnchorMouse extends GUIAnchor {
    @Override
    public void applyAnchor(GUIObject object) {
        object.position.x = Gdx.input.getX() + 10;
        object.position.y = Gdx.input.getY() - object.size.y - 10;

        if (object.position.x + object.size.x > Gdx.graphics.getWidth()) {
            object.position.x -= 20 + object.size.x;
        }

        if (object.position.y < 0) {
            object.position.y += object.size.y + 10;
        }
    }

    @Override
    public boolean needsApplyOnFrame() {
        return true;
    }
}

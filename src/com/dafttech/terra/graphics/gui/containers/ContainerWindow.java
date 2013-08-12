package com.dafttech.terra.graphics.gui.containers;

import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.graphics.gui.GUIObject;
import com.dafttech.terra.graphics.gui.elements.GUIElement;

public class ContainerWindow extends GUIContainer {
    public ContainerWindow(Vector2 p, Vector2 s) {
        super(p, s);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addObject(GUIObject e, int index) {
        if (new Rectangle(position.x, position.y, size.x, size.y).contains(new Rectangle(e.position.x, e.position.y, e.size.x, e.size.y))) {
            super.addObject(e, index);
        } else {
            throw new IllegalArgumentException("Element is outside of window");
        }
    }
}

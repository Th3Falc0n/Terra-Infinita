package com.dafttech.terra.graphics.gui.elements;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;

public interface IGUIElement {
    public void draw(AbstractScreen screen, Vector2 position);
}

package com.dafttech.terra.engine.gui.containers;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.Vector2;

public class ContainerOnscreen extends GUIContainer {
    boolean active = false;

    public ContainerOnscreen() {
        super(new Vector2(), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    public void setActive(boolean b) {
        active = b;
    }

    @Override
    public boolean providesActiveHierarchy() {
        return active;
    }
}

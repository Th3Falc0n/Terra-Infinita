package com.dafttech.terra.engine.gui.containers;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.Vector2;

public class ContainerOnscreen extends GUIContainer {
    public ContainerOnscreen() {
        super(new Vector2(), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }
}

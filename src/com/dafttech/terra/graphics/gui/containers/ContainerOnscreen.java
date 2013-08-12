package com.dafttech.terra.graphics.gui.containers;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.world.Vector2;

public class ContainerOnscreen extends GUIContainer {
    public ContainerOnscreen(Vector2 p) {
        super(p, new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }
}

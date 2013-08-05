package com.dafttech.terra.graphics.gui;

import com.dafttech.terra.world.Vector2;

public abstract class GUIObject {
    public Vector2 position;
    public Vector2 size;

    public boolean mouseHover = false;

    public GUIObject(Vector2 p, Vector2 s) {
        position = p;
        size = s;
    }

    public void onClick(int button) {

    }

    public void onMouseIn() {

    }

    public void onMouseOut() {

    }
}

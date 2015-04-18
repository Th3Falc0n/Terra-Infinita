package com.dafttech.terra.engine;

public interface IDrawableInventory {
    public void update(float delta);

    void drawInventory(Vector2 pos, AbstractScreen screen);
}

package com.dafttech.terra.engine;


public interface IDrawableInventory {
    public void update(float delta);

    void drawInventory(AbstractScreen screen, Vector2 position);
}

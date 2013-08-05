package com.dafttech.terra.graphics;

import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entities.Entity;

public interface IDrawableInventory {
    public void update(float delta);

    void drawInventory(AbstractScreen screen, Vector2 position);
}

package com.dafttech.terra.graphics;

import com.dafttech.terra.world.entities.Entity;

public interface IDrawableInventory {
    public void update(float delta);

    public void drawInventory(AbstractScreen screen, Entity pointOfView);
}

package com.dafttech.terra.graphics;

import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.game.world.entities.Entity;

public interface IDrawableInventory {
    public void update(float delta);

    void drawInventory(AbstractScreen screen, Vector2 position);
}

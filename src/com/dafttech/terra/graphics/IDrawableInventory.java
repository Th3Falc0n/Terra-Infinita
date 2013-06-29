package com.dafttech.terra.graphics;

import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.entities.Player;

public interface IDrawableInventory {
    public void update(Player player, float delta);

    public void drawInventory(AbstractScreen screen, Entity pointOfView);
}

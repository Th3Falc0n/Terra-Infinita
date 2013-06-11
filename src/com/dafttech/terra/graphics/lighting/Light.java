package com.dafttech.terra.graphics.lighting;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.entities.Player;

public abstract class Light {
    public abstract void drawToLightmap(AbstractScreen screen, Player player);
}

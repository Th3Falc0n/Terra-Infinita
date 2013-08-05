package com.dafttech.terra.graphics.lighting;

import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.graphics.AbstractScreen;

public abstract class Light {
    public abstract void drawToLightmap(AbstractScreen screen, Entity pointOfView);

    public abstract void setPosition(Vector2 p);
}

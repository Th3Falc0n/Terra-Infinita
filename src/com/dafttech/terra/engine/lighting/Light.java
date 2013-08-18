package com.dafttech.terra.engine.lighting;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;

public abstract class Light {
    Color color = Color.WHITE;
    
    public void setColor(Color c) {
        color = c;
    }
    
    public Color getColor() {
        return color;
    }
    
    public abstract void drawToLightmap(AbstractScreen screen, Entity pointOfView);

    public abstract void setPosition(Vector2 p);
}

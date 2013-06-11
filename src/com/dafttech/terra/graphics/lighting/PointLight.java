package com.dafttech.terra.graphics.lighting;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entities.Player;

public class PointLight extends Light {

    Vector2 position = new Vector2(0, 0);
    float size;
    
    public PointLight(Vector2 p, float s) {
        position.set(p);
        size = s;
    }
    
    public void setPosition(Vector2 p) {
        position.set(p);
    }
    
    @Override
    public void drawToLightmap(AbstractScreen screen, Player player) {
        Vector2 p = position.toRenderPosition(player.getPosition());
        
        screen.shr.begin(ShapeType.FilledCircle);
        screen.shr.filledCircle(p.x, p.y, size);
        screen.shr.end();
    }

}

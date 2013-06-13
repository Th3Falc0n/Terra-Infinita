package com.dafttech.terra.graphics.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.shaders.ShaderLibrary;
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
  
        screen.shr.begin(ShapeType.FilledTriangle);   
        
        float angleA = 0;
        float angleB;
        
        for(int i = 0; i < 47 ; i++) {
            angleB = angleA;
            angleA = i * 3.14159f / 23;

            float dxA = (float)Math.cos(angleA);
            float dyA = (float)Math.sin(angleA);

            float dxB = (float)Math.cos(angleB);
            float dyB = (float)Math.sin(angleB);
             
            screen.shr.filledTriangle(p.x, p.y, p.x + dxA * size, p.y + dyA * size, p.x + dxB * size, p.y + dyB * size);
        }

        
        screen.shr.end();
    }

}

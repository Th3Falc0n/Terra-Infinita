package com.dafttech.terra.entity;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IRenderable;
import com.dafttech.terra.world.Vector2;

public class Entity implements IRenderable {
    Vector2 position;
    
    public Entity(Vector2 pos) {
        position = pos;
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        // TODO Auto-generated method stub
        
    }
}

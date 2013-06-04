package com.dafttech.terra.entity;

import com.dafttech.terra.world.Vector2;

public class Entity {
    Vector2 position;
    
    public Entity(Vector2 pos) {
        position = pos;
    }

    public Vector2 getPosition() {
        return position;
    }
}

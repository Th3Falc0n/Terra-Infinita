package com.dafttech.terra.world.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class Particle extends Entity {

    public Particle(Vector2 pos, World world, Vector2 s) {
        super(pos, world, s);
        // TODO Auto-generated constructor stub
    }

    @Override
    public TextureRegion getImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void checkTerrainCollisions(World world) {

    }

}

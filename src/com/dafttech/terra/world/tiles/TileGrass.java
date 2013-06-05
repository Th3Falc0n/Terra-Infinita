package com.dafttech.terra.world.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.entity.Entity;

public class TileGrass extends Tile {
    public TileGrass(Position pos) {
        super(pos);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("grass" + new Random(hashCode()).nextInt(5));
    }

    @Override
    public boolean onCollisionWith(Entity entity) {
        return false;
    }
}

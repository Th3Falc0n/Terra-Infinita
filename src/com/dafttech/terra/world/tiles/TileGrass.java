package com.dafttech.terra.world.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Tile;

public class TileGrass extends Tile {
    public TileGrass(Position pos) {
        super(pos);
    }
    
    public TextureRegion getImage() {
        return Resources.TILES.getImage("grass"+new Random(hashCode()).nextInt(5));
    }
}

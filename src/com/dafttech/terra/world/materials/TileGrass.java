package com.dafttech.terra.world.materials;

import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Tile;

public class TileGrass extends Tile {

    public TileGrass(Position pos) {
        super(pos, Resources.TILES.getImage("grass"));
    }

}

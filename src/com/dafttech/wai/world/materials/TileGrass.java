package com.dafttech.wai.world.materials;

import com.dafttech.wai.resources.Resources;
import com.dafttech.wai.world.Position;
import com.dafttech.wai.world.Tile;

public class TileGrass extends Tile {

    public TileGrass(Position pos) {
        super(pos, Resources.TILES.getImage("grass"));
    }

}

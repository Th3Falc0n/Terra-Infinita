package com.dafttech.wai.world.materials;

import com.dafttech.wai.resources.Resources;
import com.dafttech.wai.world.Position;
import com.dafttech.wai.world.Tile;

public class TileStone extends Tile {

    public TileStone(Position pos) {
        super(pos, Resources.TILES.getImage("stone"));
    }

}

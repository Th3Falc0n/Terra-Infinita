package com.dafttech.terra.world.tiles;

import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Tile;

public class TileStone extends Tile {

    public TileStone(Position pos) {
        super(pos, Resources.TILES.getImage("stone"));
    }

}

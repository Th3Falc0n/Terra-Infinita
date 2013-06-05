package com.dafttech.terra.world.tiles;

import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Tile;

public class TileDirt extends Tile {

    public TileDirt(Position pos) {
        super(pos, Resources.TILES.getImage("dirt"));
    }
    
}
